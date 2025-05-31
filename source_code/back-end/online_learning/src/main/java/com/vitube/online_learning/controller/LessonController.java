package com.vitube.online_learning.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final S3Service s3Service;

    //    @PostMapping
    //    public ApiResponse<?> createLesson(
    //            @RequestParam("courseId") String courseId, // Nhận courseId
    //            @RequestParam("title") String title, // Nhận title
    //            @RequestParam("file") MultipartFile file // Nhận file video
    //    ) throws IOException {
    //
    //        // Tạo đối tượng LessonRequest từ các tham số đã nhận
    //        LessonRequest request = LessonRequest.builder()
    //                .courseId(courseId)
    //                .file(file)
    //                .title(title)
    //                .build();
    //
    //        // Gọi service để tạo bài học
    //        LessonResponse response = lessonService.createLesson(request);
    //
    //        return ApiResponse.builder()
    //                .status(1000)
    //                .data(response)
    //                .build();
    //    }

    //    @PostMapping
    //    public ApiResponse<String> createLesson(@RequestParam("file") MultipartFile file,
    //                                               @RequestParam("title") String title,
    //                                               @RequestParam("courseId") String courseId
    //                                               ) throws IOException {
    //
    //        lessonService.createLessonS3(
    //            LessonRequest.builder()
    //                    .file(file)
    //                    .title(title)
    //                    .courseId(courseId)
    //                    .build()
    //        );
    //        return ApiResponse.<String>builder()
    //                .status(1000)
    //                .build();
    //
    //
    //    }

    @PostMapping("/signed-url")
    public ApiResponse<?> getSignedUrl(@RequestBody Map<String, String> body) {
        String filename = body.get("filename");
        if (filename == null) {
            throw new IllegalArgumentException("Filename is required");
        }
        String response = s3Service.generatePresignedUrl(filename);
        return ApiResponse.builder().status(1000).data(response).build();
    }

    @GetMapping("/{courseId}")
    public ApiResponse<List<LessonResponse>> getLessons(@PathVariable("courseId") String courseId) {
        List<LessonResponse> responses = lessonService.getLessonOfCourse(courseId);

        return ApiResponse.<List<LessonResponse>>builder()
                .status(1000)
                .data(responses)
                .build();
    }
}
