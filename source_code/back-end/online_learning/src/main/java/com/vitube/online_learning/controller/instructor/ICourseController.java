package com.vitube.online_learning.controller.instructor;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses-instructor")
public class ICourseController {
    private final CourseService courseService;

    @GetMapping
    public ApiResponse<List<CourseResponse>> getMyCourses() {
        List<CourseResponse> response = courseService.getMyCourses();
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }
}
