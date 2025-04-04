package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ApiResponse<CourseResponse> createCourse(@RequestBody CourseRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return ApiResponse.<CourseResponse>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> response = courseService.getAllCourse();
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

}
