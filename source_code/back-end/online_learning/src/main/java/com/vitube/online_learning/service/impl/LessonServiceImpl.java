package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.LessonDTO;
import com.vitube.online_learning.dto.LessonProgressDTO;
import com.vitube.online_learning.dto.request.CreateLessonRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Lesson;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.enums.S3DeleteEnum;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.mapper.LessonMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.LessonRepository;
import com.vitube.online_learning.repository.RegisterRepository;
import com.vitube.online_learning.service.LessonProgressService;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;
import com.vitube.online_learning.service.UserService;
import com.vitube.online_learning.utils.FileUtil;
import com.vitube.online_learning.utils.VideoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lớp triển khai các phương thức liên quan đến bài học.
 */
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final S3Service s3Service;
    private final LessonMapper lessonMapper;
    private final VideoUtil videoUtil;
    private final RegisterRepository registerRepository;
    private final UserService userService;
    private final LessonProgressService lessonProgressService;

    /**
     * Tạo khóa duy nhất cho bài học.
     *
     * @return Chuỗi khóa duy nhất.
     */
    public static String generateKey() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return now.format(formatter);
    }

    /**
     * Tạo bài học mới và tải video lên S3.
     *
     * @param request Yêu cầu tạo bài học.
     * @return Đối tượng phản hồi bài học.
     * @throws IOException Lỗi xảy ra khi xử lý tệp video.
     */
    @Override
    public ApiResponse<?> createLesson(CreateLessonRequest request, MultipartFile videoFile) throws IOException {
        String key = FileUtil.generateFileName(videoFile.getOriginalFilename());

        // Upload video to S3
        String videoUrl = s3Service.uploadPrivate(videoFile, key);

        // Create temp file to get video duration
        File tempFile = File.createTempFile("video", ".mp4");
        videoFile.transferTo(tempFile);

        // Đảm bảo tệp được chuyển thành công
        if (!tempFile.exists()) {
            throw new RuntimeException("Temp file transfer failed.");
        }

        // Lấy thời lượng video
        long durationInSeconds = videoUtil.getVideoDuration(tempFile);

        // Tạo bài học
        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .course(courseRepository.findById(request.getCourseId())
                        .orElseThrow(
                                () -> new AppException(ErrorCode.NOT_FOUND)
                        ))
                .videoUrl(videoUrl)
                .duration(durationInSeconds)
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .isPreview(false) // default to false
                .build();

        Lesson addedLesson = lessonRepository.save(lesson);

        // Xóa tệp tạm
        tempFile.delete();

        LessonDTO lessonDTO = lessonMapper.entityToDto(addedLesson);
        return ApiResponse.builder()
                .status(1000)
                .data(lessonDTO)
                .build();
    }

    /**
     * Lấy danh sách bài học của một khóa học.
     *
     * @param courseId ID của khóa học.
     * @return Danh sách phản hồi bài học.
     */
    @Override
    public List<LessonResponse> getLessonOfCourse(String courseId) {
        User currentUser = userService.getCurrentUser();
        String uid = currentUser.getId();
        validateCourseAccess(uid, courseId);

        List<LessonResponse> responses = new ArrayList<>();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        course.getLessons().forEach(lesson -> {
            // get lessonProgress by uid and lessonId
            LessonProgressDTO lessonProgressDTO = lessonProgressService.getLessonProgress(uid, lesson.getId());

            LessonResponse lessonResponse = lessonMapper.lessonToLessonResponse(lesson);
            lessonResponse.setLessonProgressDTO(lessonProgressDTO);


            responses.add(lessonResponse);
        });
        Collections.sort(responses);
        return responses;
    }

    /**
     * Xóa bài học theo ID.
     *
     * @param lessonId ID của bài học.
     * @return Đối tượng phản hồi (null).
     */
    @Override
    public Void deleteLesson(String lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).
                orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        // delete video from S3
        if (lesson.getVideoUrl() != null) {
            String key = FileUtil.getKeyFromUrl(lesson.getVideoUrl());
            s3Service.deleteFile(key, S3DeleteEnum.VIDEO.name());
        }
        lessonRepository.delete(lesson);
        return null;
    }


    @Override
    public LessonDTO updateLesson(LessonDTO request, MultipartFile videoFile) throws IOException {
        Lesson lesson = lessonRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
//        lesson.setTitle(request.getTitle());
//        lesson.setDescription(request.getDescription());
        lessonMapper.dtoToEntity(request, lesson);
        if (videoFile != null && !videoFile.isEmpty()) {
            // Delete old video from S3
            String oldKey = FileUtil.getKeyFromUrl(lesson.getVideoUrl());
            s3Service.deleteFile(oldKey, S3DeleteEnum.VIDEO.name());

            // Upload new video to S3
            String newKey = FileUtil.generateFileName(videoFile.getOriginalFilename());
            String newVideoUrl = s3Service.uploadPrivate(videoFile, newKey);
            lesson.setVideoUrl(newVideoUrl);

            // Create temp file to get video duration
            File tempFile = null;
            try {
                tempFile = File.createTempFile("video", ".mp4");
                videoFile.transferTo(tempFile);

                // Ensure the file was transferred successfully
                if (!tempFile.exists()) {
                    throw new RuntimeException("Temp file transfer failed.");
                }

                // Get video duration
                long newDurationInSeconds = videoUtil.getVideoDuration(tempFile);
                lesson.setDuration(newDurationInSeconds);
            } catch (IOException e) {
                throw new RuntimeException("Error processing video file", e);
            } finally {
                // Delete temp file
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
        Lesson updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.entityToDto(updatedLesson);

    }

    /**
     * Chuyển đổi đối tượng Lesson thành LessonResponse.
     *
     * @param lesson Đối tượng bài học.
     * @return Đối tượng phản hồi bài học.
     */
    public LessonResponse lessonToLessonResponse(Lesson lesson) {
        LessonResponse lessonResponse = lessonMapper.lessonToLessonResponse(lesson);
        lessonResponse.setDuration(lesson.getDuration());
        return lessonResponse;
    }

    @Override
    public LessonDTO getSignedUrl(LessonDTO request) {
        Lesson lesson = lessonRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        User currentUser = userService.getCurrentUser();
        Course course = lesson.getCourse();
        boolean isAuthorized = registerRepository.existsByStudentIdAndCourseId(currentUser.getId(), course.getId());

        if(lesson.getIsPreview()) isAuthorized = true;

        if (courseRepository.existsByIdAndInstructorId(course.getId(), currentUser.getId())) {
            isAuthorized = true;
        }

        if (!isAuthorized) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        String presignedUrl = s3Service.generatePresignedUrl(FileUtil.getKeyFromUrl(lesson.getVideoUrl()));
        LessonDTO lessonDTO = LessonDTO.builder()
                .presignedUrl(presignedUrl)
                .build();
        return lessonDTO;

    }
    private void validateCourseAccess(String userId, String courseId) {
        boolean hasAccess =
                registerRepository.existsByStudentIdAndCourseId(userId, courseId) ||
                        courseRepository.existsByIdAndInstructorId(courseId, userId);

        if (!hasAccess) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }
}