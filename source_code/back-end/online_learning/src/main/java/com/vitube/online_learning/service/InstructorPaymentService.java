package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.InstructorPaymentRequest;
import com.vitube.online_learning.dto.response.InstructorPaymentResponse;

import java.util.List;

public interface InstructorPaymentService {
    List<InstructorPaymentResponse> getPayments(InstructorPaymentRequest request);
}
