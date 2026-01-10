package com.vitube.online_learning.controller;

import java.util.List;

import com.vitube.online_learning.dto.UserDTO;
import com.vitube.online_learning.dto.request.UserCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến người dùng.
 * Bao gồm các API để tạo, xóa, lấy danh sách, và lấy thông tin cá nhân của người dùng.
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * API tạo người dùng mới.
     * Nhận thông tin người dùng từ yêu cầu và lưu vào hệ thống.
     *
     * @param request Đối tượng chứa thông tin người dùng.
     * @return Phản hồi API chứa thông tin người dùng vừa được tạo.
     */
    @PostMapping
    public ApiResponse<?> createUser(@RequestBody UserCreationRequest request) {
        UserDTO response = userService.createUser(request);
        return ApiResponse.<UserDTO>builder().status(1000).data(response).build();
    }

    /**
     * API xóa người dùng.
     * Xóa người dùng khỏi hệ thống dựa trên ID.
     *
     * @param id ID của người dùng cần xóa.
     * @return Phản hồi API xác nhận xóa thành công.
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder().status(1000).build();
    }

    /**
     * API lấy danh sách tất cả người dùng.
     * Trả về danh sách người dùng hiện có trong hệ thống.
     *
     * @return Phản hồi API chứa danh sách người dùng.
     */
    @GetMapping
    public ApiResponse<List<UserDTO>> getAllUsers() {
        var authentaction = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentaction.getAuthorities().toString());
        return ApiResponse.<List<UserDTO>>builder()
                .status(1000)
                .data(userService.getAllUsers())
                .build();
    }

    /**
     * API lấy thông tin cá nhân của người dùng hiện tại.
     * Trả về thông tin của người dùng đang đăng nhập.
     *
     * @return Phản hồi API chứa thông tin cá nhân của người dùng.
     */
    @GetMapping("/my-info")
    public ApiResponse<UserDTO> getMyInfo() {
        UserDTO response = userService.getMyInfo();
        return ApiResponse.<UserDTO>builder().status(1000).data(response).build();
    }
}