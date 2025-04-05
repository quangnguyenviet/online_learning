package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registers")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping
    public ApiResponse<?> registerCourse(@RequestBody RegisterRequest request){
        registerService.registerCourse(request);

        return ApiResponse.builder()
                .status(1000)
                .build();
    }
}
