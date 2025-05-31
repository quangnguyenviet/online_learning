package com.vitube.online_learning.controller;

import org.springframework.web.bind.annotation.*;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.RegisterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/registers")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping()
    public ApiResponse<?> registerFreeCourse(@RequestBody RegisterRequest request) {
        registerService.createRegisterData(request);

        return ApiResponse.builder().status(1000).build();
    }

    @GetMapping("/check")
    public ApiResponse<?> checkIfRegistered(@RequestParam String courseId) {
        boolean isRegistered = registerService.isRegistered(courseId);

        return ApiResponse.builder().status(1000).data(isRegistered).build();
    }
}
