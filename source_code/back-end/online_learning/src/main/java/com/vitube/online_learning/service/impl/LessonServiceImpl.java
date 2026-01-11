package com.vitube.online_learning.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public LessonResponse createLessonS3(LessonRequest request) throws IOException {
        String key = generateKey();

        // Upload video lên S3
        String videoUrl = s3Service.uploadPrivate(request.getFile(), key);

        // Tạo tệp tạm để lấy thông tin video
        File tempFile = File.createTempFile("video", ".mp4");
        request.getFile().transferTo(tempFile);

        // Đảm bảo tệp được chuyển thành công
        if (!tempFile.exists()) {
            throw new RuntimeException("Temp file transfer failed.");
        }

        // Lấy thời lượng video
        long durationInSeconds = videoUtil.getVideoDuration(tempFile);

        // Tạo bài học
        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .course(courseRepository.findById(request.getCourseId()).orElseThrow())
                .lessonKey(key)
                .videoUrl(videoUrl)
                .duration(durationInSeconds)
                .description(request.getDescription())
                .idx(request.getOrder())
                .build();

        Lesson addedLesson = lessonRepository.save(lesson);

        // Xóa tệp tạm
        tempFile.delete();

        return lessonToLessonResponse(addedLesson);
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
        Lesson lesson = lessonRepository.findById(lessonId).get();
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
        Lesson lesson = lessonRepository.findById(lessonId).get();
        if (request.getTag() != null && request.getTag().equals("UPDATE_INDEX")) {
            lesson.setIdx(request.getOrder());
        } else {
            String key = generateKey();
            lesson.setTitle(request.getTitle());
            lesson.setDescription(request.getDescription());

            if (request.getFile() != null) {
                String videoUrl = s3Service.uploadPrivate(request.getFile(), key);
                lesson.setVideoUrl(videoUrl);

                String oldKey = lesson.getLessonKey();
                s3Service.deleteFile(oldKey);
                lesson.setLessonKey(key);
            }
        }

        lessonRepository.save(lesson);

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