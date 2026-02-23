package com.vitube.online_learning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Lớp thực thể đại diện cho thống kê của giảng viên.
 * Chứa thông tin về tháng, năm, tổng số lượt đăng ký và tổng thu nhập.
 */
@Entity
@Table(name = "instructor_statistics")
@Getter
@Setter
public class InstructorStatic {
    /**
     * ID duy nhất của thống kê.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Tháng của thống kê.
     */
    private int month;

    /**
     * Năm của thống kê.
     */
    private int year;

    /**
     * Tổng số lượt đăng ký trong tháng.
     */
    private int totalRegistrations;

    /**
     * Tổng thu nhập của giảng viên trong tháng.
     */
    private float totalEarnings;

    /**
     * Giảng viên liên kết với thống kê này.
     * Liên kết với thực thể User.
     */
    @ManyToOne
    private User instructor;
}