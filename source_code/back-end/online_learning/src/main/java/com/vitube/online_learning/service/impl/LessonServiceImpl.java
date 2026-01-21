package com.vitube.online_learning.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vitube.online_learning.dto.LessonDTO;
import com.vitube.online_learning.dto.request.CreateLessonRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.enums.S3DeleteEnum;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.utils.FileUtil;
import org.springframework.stereotype.Service;

import com.vitube.online_learning.dto.request.LessonRequest;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Lesson;
import com.vitube.online_learning.mapper.LessonMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.LessonRepository;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;
import com.vitube.online_learning.utils.VideoUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
        List<LessonResponse> responses = new ArrayList<>();

        Course course = courseRepository.findById(courseId).get();
        course.getLessons().forEach(lesson -> {
            responses.add(lessonMapper.lessonToLessonResponse(lesson));
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
        String key = FileUtil.getKeyFromUrl(lesson.getVideoUrl());
        s3Service.deleteFile(key, S3DeleteEnum.VIDEO.name());

        lessonRepository.delete(lesson);
        return null;
    }

    /**
     * Cập nhật thông tin bài học.
     *
     * @param lessonId ID của bài học.
     * @param request Yêu cầu cập nhật bài học.
     * @return Đối tượng phản hồi (null).
     * @throws IOException Lỗi xảy ra khi xử lý tệp video.
     */
    @Override
    public Void updateLesson(String lessonId, LessonRequest request) throws IOException {
        return null;
    }

    /**
     * Chuyển đổi đối tượng Lesson thành LessonResponse.
     *
     * @param lesson Đối tượng bài học.
     * @return Đối tượng phản hồi bài học.
     */
    public LessonResponse lessonToLessonResponse(Lesson lesson) {
        LessonResponse lessonResponse = lessonMapper.lessonToLessonResponse(lesson);
        lessonResponse.setDurationInSeconds(lesson.getDuration());
        return lessonResponse;
    }
}