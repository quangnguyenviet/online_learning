package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.LessonProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson-progress")
@RequiredArgsConstructor
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;

    @PostMapping("/complete/{lessonId}")
    public ApiResponse<?> markLessonCompleted(@PathVariable String lessonId) {
        return lessonProgressService.markLessonCompleted(lessonId);
    }

}
