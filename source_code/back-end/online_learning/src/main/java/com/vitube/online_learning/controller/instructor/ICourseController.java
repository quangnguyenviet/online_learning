package com.vitube.online_learning.controller.instructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.request.UpdateCoursePrice;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.service.CourseService;

import lombok.RequiredArgsConstructor;

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

    @PutMapping("/price")
    public ApiResponse<?> setPrice(@RequestBody UpdateCoursePrice request) {
        String courseId = request.getCourseId();
        Float price = request.getPrice();
        courseService.setPrice(courseId, price);
        return ApiResponse.builder().status(1000).build();
    }

    /*@PostMapping
    public ApiResponse<?> createCourse(@RequestBody CourseRequest request) {
    	courseService.createCourse(request);
    	return ApiResponse.builder()
    			.status(1000)
    			.build();
    }*/
    @PostMapping
    public ApiResponse<?> createCourse(
            @RequestPart("request") CourseRequest request, @RequestPart("image") MultipartFile image) {
        courseService.createCourse(request, image);
        return ApiResponse.builder().status(1000).build();
    }

    @PutMapping("/{courseId}")
    public ApiResponse<?> updateCourse(@PathVariable String courseId, @RequestBody CourseRequest request) {
        courseService.updateCourse(courseId, request);
        return ApiResponse.builder().status(1000).build();
    }
}
