package com.vitube.online_learning.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String lessonKey;
    private String videoUrl;
    private Integer idx;

    @Column(columnDefinition = "TEXT")
    private String description;

    private long duration;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
}
