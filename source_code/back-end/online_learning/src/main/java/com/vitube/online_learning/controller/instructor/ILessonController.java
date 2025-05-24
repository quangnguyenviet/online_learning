package com.vitube.online_learning.controller.instructor;

import com.vitube.online_learning.dto.request.LessonRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.LessonResponse;
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
                                       @RequestParam(value = "title", required = false) String title,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "order", required = false) Integer order,
                                       @RequestParam(value = "tag", required = false) String tag
                                       ) throws IOException {
        LessonRequest request = LessonRequest.builder()
                .file(file)
                .title(title)
                .description(description)
                .order(order)
                .tag(tag)
                .build();
        lessonService.updateLesson(lessonId, request);

        return ApiResponse.builder()
                .status(1000)
                .build();
    }

    @PostMapping
    public ApiResponse<LessonResponse> createLesson(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("title") String title,
                                                    @RequestParam("courseId") String courseId,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("order") int order
    ) throws IOException {

        LessonResponse response = lessonService.createLessonS3(
                LessonRequest.builder()
                        .file(file)
                        .title(title)
                        .courseId(courseId)
                        .description(description)
                        .order(order)
                        .build()
        );
        return ApiResponse.<LessonResponse>builder()
                .status(1000)
                .data(response)
                .build();


    }
}
