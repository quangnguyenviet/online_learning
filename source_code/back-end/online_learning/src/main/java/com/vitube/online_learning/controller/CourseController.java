package com.vitube.online_learning.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.service.CourseService;

import lombok.RequiredArgsConstructor;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến khóa học.
 * Bao gồm các API để lấy danh sách khóa học, tìm kiếm, chi tiết khóa học, và các khóa học miễn phí hoặc nâng cao.
 */
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    /**
     * API lấy danh sách khóa học.
     * Cho phép lọc theo loại khóa học hoặc tìm kiếm theo từ khóa.
     *
     * @param type Loại khóa học (tùy chọn).
     * @param query Từ khóa tìm kiếm (tùy chọn).
     * @return Phản hồi API chứa danh sách khóa học.
     */
    @GetMapping
    public ApiResponse<List<CourseResponse>> getCourses(
            @RequestParam(required = false) String type, @RequestParam(required = false) String query) {
        List<CourseResponse> response = courseService.getCourses(type, query);
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    /**
     * API lấy thông tin chi tiết của khóa học.
     *
     * @param id ID của khóa học.
     * @return Phản hồi API chứa thông tin chi tiết của khóa học.
     */
    @GetMapping("/{id}")
    public ApiResponse<CourseResponse> getCourseById(@PathVariable String id) {
        CourseResponse response = courseService.getCourseById(id);
        return ApiResponse.<CourseResponse>builder().status(1000).data(response).build();
    }

    /**
     * API lấy danh sách các khóa học miễn phí.
     *
     * @return Phản hồi API chứa danh sách các khóa học miễn phí.
     */
    @GetMapping("/free")
    public ApiResponse<List<CourseResponse>> getFreeCourses() {
        List<CourseResponse> response = courseService.getFreeCourse();
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    /**
     * API lấy danh sách các khóa học nâng cao (Plus).
     *
     * @return Phản hồi API chứa danh sách các khóa học nâng cao.
     */
    @GetMapping("/plus")
    public ApiResponse<List<CourseResponse>> getPlusCourses() {
        List<CourseResponse> response = courseService.getPlusCourse();
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    /**
     * API lấy danh sách các khóa học đang học.
     *
     * @return Phản hồi API chứa danh sách các khóa học đang học.
     */
    @GetMapping("/learning")
    public ApiResponse<List<CourseResponse>> getLearningCourses() {
        List<CourseResponse> response = courseService.getLearningCourses();
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    /**
     * API lấy danh sách các khóa học của giảng viên.
     *
     * @param instructorId ID của giảng viên.
     * @return Phản hồi API chứa danh sách các khóa học của giảng viên.
     */
    @GetMapping("/instructor/{instructorId}")
    public ApiResponse<List<CourseResponse>> getCoursesOfInstructor(@PathVariable String instructorId) {
        List<CourseResponse> response = courseService.getCoursesOfInstructor(instructorId);
        return ApiResponse.<List<CourseResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }
}