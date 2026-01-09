package com.vitube.online_learning.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

/**
 * Lớp thực thể đại diện cho khóa học trong hệ thống.
 * Chứa thông tin về khóa học như tiêu đề, giá, giảm giá, trạng thái xuất bản, và các liên kết với các thực thể khác.
 */
@Table(name = "course")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    /**
     * ID duy nhất của khóa học.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Tiêu đề của khóa học.
     */
    private String title;

    /**
     * URL hình ảnh đại diện của khóa học.
     */
    private String imageUrl;

    /**
     * Trạng thái xuất bản của khóa học.
     * True nếu đã xuất bản, false nếu chưa.
     */
    private Boolean published;

    /**
     * Giảng viên tạo khóa học.
     * Liên kết với thực thể User.
     */
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    /**
     * Giá của khóa học.
     */
    private float price;

    /**
     * Mức giảm giá của khóa học (theo phần trăm).
     */
    private int discount;

    /**
     * Phương thức tính giá mới sau khi áp dụng giảm giá.
     *
     * @return Giá mới sau khi giảm giá.
     */
    public long getNewPrice() {
        return (long) (price * ((100 - discount) * 1.0 / 100));
    }

    /**
     * Danh sách các đăng ký liên quan đến khóa học.
     * Liên kết với thực thể Register.
     */
    @OneToMany(mappedBy = "course")
    private Set<Register> registers;

    /**
     * Danh sách các bài học trong khóa học.
     * Liên kết với thực thể Lesson.
     */
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Lesson> lessons;

    /**
     * Danh sách các nội dung học được trong khóa học.
     * Liên kết với thực thể LearnWhat.
     */
    @OneToMany(mappedBy = "course")
    private Set<LearnWhat> learnWhats;

    /**
     * Mô tả chi tiết về khóa học.
     * Lưu trữ dưới dạng văn bản dài (TEXT).
     */
    @Column(columnDefinition = "TEXT")
    private String longDesc;

    /**
     * Mô tả ngắn gọn về khóa học.
     */
    private String shortDesc;

    /**
     * Danh sách các yêu cầu cần thiết cho khóa học.
     * Liên kết với thực thể Require.
     */
    @OneToMany(mappedBy = "course")
    private Set<Require> requires;
}