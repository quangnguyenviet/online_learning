package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.LessonDTO;
import com.vitube.online_learning.dto.request.CreateLessonRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến bài học.
 * Bao gồm các API để lấy danh sách bài học, tạo bài học mới, và tạo URL có chữ ký để tải lên file video.
 */
@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Slf4j
public class LessonController {
    private final LessonService lessonService;
    private final S3Service s3Service;


    @PostMapping("/signed-url")
    public ApiResponse<?> getSignedUrl(@RequestBody LessonDTO request) {
        LessonDTO response = lessonService.getSignedUrl(request);

        return ApiResponse.builder().status(1000)
                .data(response)
                .build();
    }

    @GetMapping("/course/{courseId}")
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
        log.info("Inside createLesson (LessonController)");
        ApiResponse<?> response = lessonService.createLesson(request, videoFile);
        return response;

    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    public ApiResponse<?> deleteLesson(@PathVariable String lessonId) {
        lessonService.deleteLesson(lessonId);
        return ApiResponse.builder().status(1000).build();
    }

    @PutMapping(value = "/{lessonId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    public ApiResponse<?> updateLesson(
            @PathVariable String lessonId,
           @ModelAttribute LessonDTO request,
            @RequestPart(value = "videoFile", required = false) MultipartFile videoFile
            ) throws IOException {
        request.setId(lessonId);
        LessonDTO response = lessonService.updateLesson(request, videoFile);

        return ApiResponse.<LessonDTO>builder()
                .status(1000)
                .data(response)
                .build();
    }

    // update lesson isPreview
    @PatchMapping("/{lessonId}/preview")
    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    public ApiResponse<?> updateLessonIsPreview(@PathVariable String lessonId, @RequestBody LessonDTO request ) {
        lessonService.updateLessonPreview(lessonId, request.getIsPreview());
        return ApiResponse.builder()
                .status(1000)
                .build();
    }
}