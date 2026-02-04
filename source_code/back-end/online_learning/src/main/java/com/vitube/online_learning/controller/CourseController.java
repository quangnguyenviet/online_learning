package com.vitube.online_learning.controller;

import java.util.List;

import com.vitube.online_learning.dto.CourseDTO;
import com.vitube.online_learning.dto.ObjectiveDTO;
import com.vitube.online_learning.dto.request.CourseCreattionRequest;
import com.vitube.online_learning.dto.request.UpdateCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.CourseService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến khóa học.
 * Bao gồm các API để lấy danh sách khóa học, tìm kiếm, chi tiết khóa học, và các khóa học miễn phí hoặc nâng cao.
 */
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
//    public ApiResponse<List<CourseDTO>> getCourses(
//            @RequestParam(required = false) String type, @RequestParam(required = false) String query) {
//        List<CourseDTO> response = courseService.getCourses(type, query);
//        return ApiResponse.<List<CourseDTO>>builder()
//                .status(1000)
//                .data(response)
//                .build();
//    }

//    Response<Page<OrderDTO>> getAllOrders(OrderStatus orderStatus, int page, int size);

    public ApiResponse<Page<CourseDTO>> getCourses(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String query, // query is a search keyword
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<CourseDTO> response = courseService.getCourses(type, query, page, size);

        return ApiResponse.<Page<CourseDTO>>builder()
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
    public ApiResponse<CourseDTO> getCourseById(@PathVariable String id) {
        CourseDTO response = courseService.getCourseById(id);
        return ApiResponse.<CourseDTO>builder().status(1000).data(response).build();
    }

    /**
     * API lấy danh sách các khóa học miễn phí.
     *
     * @return Phản hồi API chứa danh sách các khóa học miễn phí.
     */
    @GetMapping("/free")
    public ApiResponse<List<CourseDTO>> getFreeCourses() {
        List<CourseDTO> response = courseService.getFreeCourse();
        return ApiResponse.<List<CourseDTO>>builder()
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
    public ApiResponse<List<CourseDTO>> getPlusCourses() {
        List<CourseDTO> response = courseService.getPlusCourse();
        return ApiResponse.<List<CourseDTO>>builder()
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
    public ApiResponse<List<CourseDTO>> getLearningCourses() {
        List<CourseDTO> response = courseService.getLearningCourses();
        return ApiResponse.<List<CourseDTO>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    @GetMapping("/my-courses")
    public ApiResponse<List<CourseDTO>> getMyCourses() {
        List<CourseDTO> response = courseService.getMyCourses();
        return ApiResponse.<List<CourseDTO>>builder()
                .status(1000)
                .data(response)
                .build();
    }



    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> createCourse(
            @ModelAttribute CourseCreattionRequest request, @RequestPart("imageFile") MultipartFile imageFile) {
        CourseDTO response = courseService.createCourse(request, imageFile);
        return ApiResponse.builder().status(1000)
                .data(response)
                .build();
    }


    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CourseDTO> updateCourse(
                                       @ModelAttribute UpdateCourseRequest request,
                                       @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
                                       @RequestPart(value = "test", required = false) String test,
                                       @RequestPart(value = "updatedObjectives", required = false) List<ObjectiveDTO> updatedObjectives
                                       ) {
        CourseDTO response = courseService.updateCourse(request, imageFile, updatedObjectives);
        return ApiResponse.<CourseDTO>builder().status(1000)
                .data(response)
                .build();
    }

}
