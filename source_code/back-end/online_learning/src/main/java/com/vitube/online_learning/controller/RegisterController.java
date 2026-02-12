package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến đăng ký khóa học.
 * Bao gồm các API để đăng ký khóa học miễn phí và kiểm tra trạng thái đăng ký.
 */
@RestController
@RequestMapping("/registers")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    /**
     * API đăng ký khóa học miễn phí.
     * Nhận thông tin đăng ký từ người dùng và lưu vào hệ thống.
     *
     * @param request Đối tượng chứa thông tin đăng ký.
     * @return Phản hồi API xác nhận đăng ký thành công.
     */
    @PostMapping()
    public ApiResponse<?> registerFreeCourse(@RequestBody RegisterRequest request) {
//        registerService.createRegisterData(request);

        return ApiResponse.builder().status(1000).build();
    }

    /**
     * API kiểm tra trạng thái đăng ký.
     * Kiểm tra xem người dùng đã đăng ký khóa học hay chưa dựa trên ID khóa học.
     *
     * @param courseId ID của khóa học.
     * @return Phản hồi API chứa trạng thái đăng ký (true/false).
     */
    @GetMapping("/check")
    public ApiResponse<?> checkIfRegistered(@RequestParam String courseId) {
        boolean isRegistered = registerService.isRegistered(courseId);

        return ApiResponse.builder().status(1000).data(isRegistered).build();
    }
}