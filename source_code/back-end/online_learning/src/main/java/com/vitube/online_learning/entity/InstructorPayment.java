package com.vitube.online_learning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Lớp thực thể đại diện cho thông tin thanh toán của giảng viên.
 * Chứa thông tin về thanh toán và liên kết với thống kê của giảng viên.
 */
@Entity
@Table(name = "instructor_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorPayment {

    /**
     * ID duy nhất của thanh toán.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Thống kê liên quan đến thanh toán của giảng viên.
     * Liên kết với thực thể InstructorStatic.
     */
    @OneToOne
    @JoinColumn(name = "statistic_id", referencedColumnName = "id", unique = true, nullable = false)
    private InstructorStatic statistic;

    /**
     * Thời gian thanh toán.
     */
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}