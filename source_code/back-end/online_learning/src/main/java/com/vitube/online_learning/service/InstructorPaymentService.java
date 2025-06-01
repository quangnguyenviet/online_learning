package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.InstructorPaymentRequest;
import com.vitube.online_learning.dto.response.InstructorPaymentResponse;

import java.util.List;

/**
 * Interface cung cấp các phương thức liên quan đến thanh toán của giảng viên.
 */
public interface InstructorPaymentService {
    /**
     * Lấy danh sách các khoản thanh toán của giảng viên.
     *
     * @param request Yêu cầu lấy thông tin thanh toán.
     * @return Danh sách phản hồi thanh toán của giảng viên.
     */
    List<InstructorPaymentResponse> getPayments(InstructorPaymentRequest request);

    /**
     * Cập nhật thông tin thanh toán của giảng viên.
     *
     * @param request Yêu cầu cập nhật thông tin thanh toán.
     */
    void updatePayment(InstructorPaymentRequest request);
}