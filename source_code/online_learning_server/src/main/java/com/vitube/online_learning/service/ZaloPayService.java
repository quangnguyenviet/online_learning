package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.ApiResponse;

/**
 * Interface cung cấp các phương thức liên quan đến dịch vụ ZaloPay.
 */
public interface ZaloPayService {

    /**
     * Tạo đơn hàng mới cho một khóa học.
     *
     * @param courseId ID của khóa học.
     * @return Thông tin đơn hàng dưới dạng Map.
     * @throws Exception Lỗi xảy ra khi tạo đơn hàng.
     */
    ApiResponse<?> createOrder(String courseId) throws Exception;
}