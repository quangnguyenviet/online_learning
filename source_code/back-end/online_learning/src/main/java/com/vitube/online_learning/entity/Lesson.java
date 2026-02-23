package com.vitube.online_learning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String lessonKey;

    private String videoUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private long duration;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Boolean isPreview;

    private LocalDateTime createdAt;

//    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<LessonProgress> lessonProgress;
}
