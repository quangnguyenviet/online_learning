package com.vitube.online_learning.controller.admin;

import com.vitube.online_learning.dto.request.InstructorPaymentRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.InstructorPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin-instructor-payment")
@RequiredArgsConstructor
public class AInstructorPaymentController {
    private final InstructorPaymentService instructorPaymentService;

    // lấy danh sách cần thanh toán
    @GetMapping
    public ApiResponse<?> getByMonthAndYear(@RequestBody InstructorPaymentRequest request){
        List<?> payments = instructorPaymentService.getPayments(request);

        return ApiResponse.builder()
                .status(1000)
                .data(payments)
                .build();


    }

}
