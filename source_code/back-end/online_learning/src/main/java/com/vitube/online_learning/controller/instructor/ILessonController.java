package com.vitube.online_learning.controller.instructor;

import com.vitube.online_learning.dto.request.LessonRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instructor-lesson")
public class ILessonController {
    private final LessonService lessonService;

    @DeleteMapping("/{lessonId}")
    public ApiResponse<?> deleteLesson(@PathVariable String lessonId) {
        lessonService.deleteLesson(lessonId);
        return ApiResponse.builder()
                .status(1000)
                .build();
    }

    @PutMapping("/{lessonId}")
    public ApiResponse<?> updateLesson(@PathVariable String lessonId,
                                        @RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam("title") String title,
                                       @RequestParam("description") String description
                                       ) throws IOException {
        LessonRequest request = LessonRequest.builder()
                .file(file)
                .title(title)
                .description(description)
                .build();
        lessonService.updateLesson(lessonId, request);

        return ApiResponse.builder()
                .status(1000)
                .build();
    }

    @PostMapping
    public ApiResponse<String> createLesson(@RequestParam("file") MultipartFile file,
                                            @RequestParam("title") String title,
                                            @RequestParam("courseId") String courseId
    ) throws IOException {

        lessonService.createLessonS3(
                LessonRequest.builder()
                        .file(file)
                        .title(title)
                        .courseId(courseId)
                        .build()
        );
        return ApiResponse.<String>builder()
                .status(1000)
                .build();


    }
}
