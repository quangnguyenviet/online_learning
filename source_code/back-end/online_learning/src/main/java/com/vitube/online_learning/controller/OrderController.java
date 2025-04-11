package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.request.OrderRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ApiResponse<?> createOrder(@RequestBody OrderRequest request){
        return null;
    }
}
