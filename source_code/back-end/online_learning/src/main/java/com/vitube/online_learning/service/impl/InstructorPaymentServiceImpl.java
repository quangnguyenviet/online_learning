package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.InstructorPaymentRequest;
import com.vitube.online_learning.dto.response.InstructorPaymentResponse;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.InstructorPayment;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.InstructorPaymentRepository;
import com.vitube.online_learning.service.InstructorPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InstructorPaymentServiceImpl implements InstructorPaymentService {
    private final InstructorPaymentRepository instructorPaymentRepository;

    @Override
    public List<InstructorPaymentResponse> getPayments(InstructorPaymentRequest request) {
        List<Map<String, Object>> paymentData = instructorPaymentRepository.getInstructorPaymentInfo(
                request.getMonth(), request.getYear()
        );
        List<InstructorPaymentResponse> responses = new ArrayList<>();
        if (paymentData != null && !paymentData.isEmpty()) {
            for (Map<String, Object> data : paymentData) {
                InstructorPaymentResponse response = new InstructorPaymentResponse();

                response.setPaidAt(data.get("paid_at") != null ?
                        ((java.sql.Timestamp) data.get("paid_at")).toLocalDateTime() : null);

                UserResponse instructor = new UserResponse();
                instructor.setFirstName((String) data.get("first_name"));
                instructor.setLastName((String) data.get("last_name"));
                instructor.setEmail((String) data.get("email"));
                response.setInstructor(instructor);


                responses.add(response);
            }
            return responses;
        }
        return null;
    }
}
