package com.vitube.online_learning.dto.request;

import lombok.*;

/**
 * Lớp yêu cầu cho thông tin khóa học.
 * Chứa các trường dữ liệu cần thiết để tạo hoặc cập nhật khóa học.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequest extends BaseRequest {
    /**
     * Tiêu đề của khóa học.
     */
    private String title;

    /**
     * ID của giảng viên tạo khóa học.
     */
    private String instructorId;

    /**
     * Giá của khóa học.
     */
    private Double price;

    /**
     * Mức giảm giá của khóa học (theo phần trăm).
     */
    private Integer discount;

    /**
     * Mô tả ngắn gọn về khóa học.
     */
    private String shortDesc;

    /**
     * Trạng thái xuất bản của khóa học.
     * Mặc định là chưa xuất bản (false).
     */
    private Boolean published = false;
}