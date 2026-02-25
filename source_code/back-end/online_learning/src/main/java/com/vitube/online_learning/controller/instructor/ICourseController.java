package com.vitube.online_learning.controller.instructor;

import com.vitube.online_learning.dto.CourseDTO;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructor/courses")
@RequiredArgsConstructor
public class ICourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    @GetMapping("/my-courses")
    public ApiResponse<?> getMyCourses(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Page<?> response = courseService.getMyCourses(page, size);
        return ApiResponse.builder()
                .status(1000)
                .data(response)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    @PatchMapping("/{id}/publish")
    public ApiResponse<CourseDTO> updateCoursePublishedStatus(
            @PathVariable String id,
            @RequestParam(defaultValue = "true") Boolean published) {
        CourseDTO response = courseService.updatePublishedStatus(id, published);
        return ApiResponse.<CourseDTO>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CourseDTO> getCourseById(@PathVariable String id) {
        CourseDTO response = courseService.getCourseById(id);
        return ApiResponse.<CourseDTO>builder().status(1000).data(response).build();
    }

}
