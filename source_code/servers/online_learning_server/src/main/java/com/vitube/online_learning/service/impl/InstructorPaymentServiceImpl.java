package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.InstructorPaymentRequest;
import com.vitube.online_learning.dto.response.InstructorPaymentResponse;
import com.vitube.online_learning.dto.response.InstructorStatisticResponse;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.InstructorPayment;
import com.vitube.online_learning.repository.InstructorPaymentRepository;
import com.vitube.online_learning.service.InstructorPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Lớp triển khai các phương thức liên quan đến thanh toán của giảng viên.
 */
@Service
@RequiredArgsConstructor
public class InstructorPaymentServiceImpl implements InstructorPaymentService {
    private final InstructorPaymentRepository instructorPaymentRepository;

    /**
     * Lấy danh sách thanh toán của giảng viên theo tháng và năm.
     *
     * @param request Yêu cầu chứa thông tin tháng và năm.
     * @return Danh sách phản hồi thanh toán của giảng viên.
     */
    @Override
    public List<InstructorPaymentResponse> getPayments(InstructorPaymentRequest request) {
        List<Map<String, Object>> paymentData = instructorPaymentRepository.getInstructorPaymentInfo(
                request.getMonth(), request.getYear()
        );
        List<InstructorPaymentResponse> responses = new ArrayList<>();
        if (paymentData != null && !paymentData.isEmpty()) {
            for (Map<String, Object> data : paymentData) {
                InstructorPaymentResponse response = new InstructorPaymentResponse();

                // Thiết lập thông tin thanh toán
                response.setId((String) data.get("statisticId"));

                response.setPaidAt(data.get("paid_at") != null ?
                        ((java.sql.Timestamp) data.get("paid_at")).toLocalDateTime() : null);

                // Thiết lập thông tin giảng viên
                UserResponse instructor = new UserResponse();
                instructor.setFirstName((String) data.get("first_name"));
                instructor.setLastName((String) data.get("last_name"));
                instructor.setEmail((String) data.get("email"));
                instructor.setId((String) data.get("instructorId"));
                instructor.setAccountNumber((String) data.get("account_number"));
                instructor.setAccountName((String) data.get("account_name"));
                instructor.setBankName((String) data.get("bank_name"));
                response.setInstructor(instructor);

                // Thiết lập thông tin thống kê
                InstructorStatisticResponse statistic = new InstructorStatisticResponse();
                statistic.setMonth((Integer) data.get("month"));
                statistic.setYear((Integer) data.get("year"));
                statistic.setTotalEarnings((float) (data.get("total_earnings") != null ?
                        ((Number) data.get("total_earnings")).doubleValue() : 0.0));
                response.setStatistic(statistic);

                responses.add(response);
            }
            return responses;
        }
        return null;
    }

    /**
     * Cập nhật thông tin thanh toán của giảng viên.
     *
     * @param request Yêu cầu chứa thông tin thanh toán cần cập nhật.
     */
    @Override
    public void updatePayment(InstructorPaymentRequest request) {
        // Cập nhật thời gian thanh toán
        InstructorPayment payment = instructorPaymentRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaidAt(request.getPaidAt());
        // Lưu thông tin thanh toán
        instructorPaymentRepository.save(payment);
        return;
    }
}