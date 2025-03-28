package com.vitube.online_learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest request) {

        UserResponse response = userService.createUser(request);

        return ApiResponse.<UserResponse>builder().status(1000).data(response).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder().status(1000).build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {

        var authentaction = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentaction.getAuthorities().toString());
        return ApiResponse.<List<UserResponse>>builder()
                .status(1000)
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        UserResponse response = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder().status(1000).data(response).build();
    }
}
