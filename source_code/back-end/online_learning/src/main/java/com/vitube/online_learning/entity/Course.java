package com.vitube.online_learning.entity;

import com.vitube.online_learning.enums.LevelEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String imageUrl;

    private Boolean published;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    /**
     * Giá của khóa học.
     */
    private Double price;

    /**
     * Mức giảm giá của khóa học (theo phần trăm).
     */
    private Integer discount;

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
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Objective> objectives;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    private LocalDateTime createdAt;
}