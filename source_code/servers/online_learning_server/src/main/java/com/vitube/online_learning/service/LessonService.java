package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.LessonDTO;
import com.vitube.online_learning.dto.request.CreateLessonRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Lesson;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Interface cung cấp các phương thức liên quan đến bài học.
 */
public interface LessonService {
    /**
     * Tạo mới một bài học và lưu trữ trên S3.
     *
     * @param request Yêu cầu tạo bài học.
     * @return Đối tượng phản hồi bài học.
     * @throws IOException Lỗi xảy ra khi xử lý tệp.
     */
    ApiResponse<?> createLesson(CreateLessonRequest request, MultipartFile videoFile) throws IOException;

    /**
     * Lấy danh sách các bài học của một khóa học.
     *
     * @param courseId ID của khóa học.
     * @return Danh sách phản hồi bài học.
     */
    List<LessonResponse> getLessonOfCourse(String courseId);

    /**
     * Xóa bài học theo ID.
     *
     * @param lessonId ID của bài học.
     * @return Void.
     */
    Void deleteLesson(String lessonId);


    LessonDTO updateLesson(LessonDTO request, MultipartFile videoFile) throws IOException;

    /**
     * Chuyển đổi đối tượng Lesson thành LessonResponse.
     *
     * @param lesson Đối tượng bài học.
     * @return Đối tượng phản hồi bài học.
     */
    LessonResponse lessonToLessonResponse(Lesson lesson);

    LessonDTO getSignedUrl(LessonDTO request);

    void updateLessonPreview(String lessonId, Boolean isPreview);
}