package com.vitube.online_learning.controller;

import java.util.List;

import com.vitube.online_learning.dto.UserDTO;
import com.vitube.online_learning.dto.request.UserCreationRequest;
import com.vitube.online_learning.dto.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ApiResponse<?> createUser(@RequestBody UserCreationRequest request) {
        UserDTO response = userService.createUser(request);
        return ApiResponse.<UserDTO>builder().status(1000).data(response).build();
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder().status(1000).build();
    }

    @GetMapping
    public ApiResponse<List<UserDTO>> getAllUsers() {
        var authentaction = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentaction.getAuthorities().toString());
        return ApiResponse.<List<UserDTO>>builder()
                .status(1000)
                .data(userService.getAllUsers())
                .build();
    }


    @GetMapping("/my-info")
    public ApiResponse<UserDTO> getMyInfo() {
        UserDTO response = userService.getMyInfo();
        return ApiResponse.<UserDTO>builder().status(1000).data(response).build();
    }

    @PutMapping("/my-info")
    public ApiResponse<UserDTO> updateMyInfo(@RequestBody UpdateUserRequest request) {
        UserDTO response = userService.updateMyInfo(request);
        return ApiResponse.<UserDTO>builder().status(1000).data(response).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable String id) {
//        UserDTO response = userService.getUserById(id);
//        return ApiResponse.<UserDTO>builder().status(1000).data(response).build();
        return null;
    }

    @GetMapping("/{id}/stats")
    public ApiResponse<UserResponse> getUserStats(@PathVariable String id) {
//        UserResponse response = userService.getUserStats(id);
//        return ApiResponse.<UserResponse>builder().status(1000).data(response).build();
        return null;
    }

    @GetMapping("/{id}/enrolled-courses")
    public ApiResponse<List<String>> getEnrolledCourses(@PathVariable String id) {
//        List<String> response = userService.getEnrolledCourses(id);
//        return ApiResponse.<List<String>>builder().status(1000).data(response).build();
        return null;
    }
}