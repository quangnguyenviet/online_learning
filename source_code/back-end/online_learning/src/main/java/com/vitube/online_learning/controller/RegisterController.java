package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registers")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/free")
    public ApiResponse<?> registerFreeCourse(@RequestBody RegisterRequest request){
        registerService.registerFreeCourse(request);

        return ApiResponse.builder()
                .status(1000)
                .build();
    }

    @GetMapping("/check")
    public ApiResponse<?> checkIfRegistered(@RequestParam String courseId) {
        boolean isRegistered = registerService.isRegistered(courseId);

        return ApiResponse.builder()
                .status(1000)
                .data(isRegistered)
                .build();
    }



}
