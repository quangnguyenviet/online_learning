package com.vitube.online_learning.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.vitube.online_learning.dto.LessonDTO;
import com.vitube.online_learning.dto.request.CreateLessonRequest;
import com.vitube.online_learning.dto.request.LessonRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến bài học.
 * Bao gồm các API để lấy danh sách bài học, tạo bài học mới, và tạo URL có chữ ký để tải lên file video.
 */
@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final S3Service s3Service;

    /**
     * API tạo URL có chữ ký để tải lên file video.
     * Nhận tên file từ yêu cầu và trả về URL có chữ ký.
     *
     * @param body Đối tượng chứa tên file.
     * @return Phản hồi API chứa URL có chữ ký.
     */
    @PostMapping("/signed-url")
    public ApiResponse<?> getSignedUrl(@RequestBody Map<String, String> body) {
        String videoUrl = body.get("videoUrl");
        if (videoUrl == null) {
            throw new IllegalArgumentException("Filename is required");
        }
        String response = s3Service.generatePresignedUrl(videoUrl);
        return ApiResponse.builder().status(1000)
                .data(LessonDTO.builder()
                    .presignedUrl(response)
                        .build()
                )
                .build();
    }

    /**
     * API lấy danh sách bài học của một khóa học.
     *
     * @param courseId ID của khóa học.
     * @return Phản hồi API chứa danh sách bài học.
     */
    @GetMapping("/{courseId}")
    public ApiResponse<List<LessonResponse>> getLessons(@PathVariable("courseId") String courseId) {
        List<LessonResponse> responses = lessonService.getLessonOfCourse(courseId);

        return ApiResponse.<List<LessonResponse>>builder()
                .status(1000)
                .data(responses)
                .build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    public ApiResponse<?> createLesson(
            @ModelAttribute CreateLessonRequest request,
            @RequestPart(value = "videoFile", required = true) MultipartFile videoFile
            )
            throws IOException {
        return lessonService.createLesson(request, videoFile);

    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    public ApiResponse<?> deleteLesson(@PathVariable String lessonId) {
        lessonService.deleteLesson(lessonId);
        return ApiResponse.builder().status(1000).build();
    }
}