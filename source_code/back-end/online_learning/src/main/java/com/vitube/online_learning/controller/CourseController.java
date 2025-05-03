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
    public ApiResponse<List<CourseResponse>> getAllCourses(@RequestParam(required = false) String key) {
        List<CourseResponse> response = courseService.getAllCourse(key);
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CourseResponse> getCourseById(@PathVariable String id) {
        CourseResponse response = courseService.getCourseById(id);
        return ApiResponse.<CourseResponse>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @GetMapping("/free")
    public ApiResponse<List<CourseResponse>> getFreeCourses() {
        List<CourseResponse> response = courseService.getFreeCourse();
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @GetMapping("/plus")
    public ApiResponse<List<CourseResponse>> getPlusCourses() {
        List<CourseResponse> response = courseService.getPlusCourse();
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @GetMapping("/learning")
    public ApiResponse<List<CourseResponse>> getLearningCourses() {
        List<CourseResponse> response = courseService.getLearningCourses();
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @GetMapping("/instructor/{instructorId}")
    public ApiResponse<List<CourseResponse>> getCoursesOfInstructor(@PathVariable String instructorId) {
        List<CourseResponse> response = courseService.getCoursesOfInstructor(instructorId);
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }


}
