package com.vitube.online_learning.entity;

import jakarta.persistence.*;

import lombok.*;

/**
 * Lớp thực thể đại diện cho bài học trong khóa học.
 * Chứa thông tin về tiêu đề, khóa học liên kết, video, mô tả, và thời lượng.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lesson")
public class Lesson {
    /**
     * ID duy nhất của bài học.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Tiêu đề của bài học.
     */
    private String title;

    /**
     * Khóa học liên kết với bài học.
     */
    private String lessonKey;

    /**
     * URL video của bài học.
     */
    private String videoUrl;

    /**
     * Thứ tự của bài học trong khóa học.
     */
    private Integer idx;

    /**
     * Mô tả chi tiết về bài học.
     * Lưu trữ dưới dạng văn bản dài (TEXT).
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Thời lượng của bài học (tính bằng giây).
     */
    private long duration;

    /**
     * Khóa học mà bài học thuộc về.
     * Liên kết với thực thể Course.
     */
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
}