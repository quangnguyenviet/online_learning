package com.vitube.online_learning.controller.instructor;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
