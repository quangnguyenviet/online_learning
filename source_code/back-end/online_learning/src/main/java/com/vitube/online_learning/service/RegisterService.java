package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.entity.Register;

/**
 * Interface cung cấp các phương thức liên quan đến đăng ký khóa học.
 */
public interface RegisterService {
    /**
     * Chuyển đổi đối tượng RegisterRequest thành Register.
     *
     * @param request Yêu cầu đăng ký.
     * @return Đối tượng đăng ký.
     */
    Register toEntity(RegisterRequest request);

    /**
     * Tạo dữ liệu đăng ký mới.
     *
     * @param request Yêu cầu đăng ký.
     */
    void createRegisterData(RegisterRequest request);

    /**
     * Kiểm tra xem người dùng đã đăng ký khóa học hay chưa.
     *
     * @param courseId ID của khóa học.
     * @return `true` nếu đã đăng ký, ngược lại `false`.
     */
    boolean isRegistered(String courseId);
}