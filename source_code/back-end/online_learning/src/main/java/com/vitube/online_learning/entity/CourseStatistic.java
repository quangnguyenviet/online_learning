package com.vitube.online_learning.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_statistic")
@Getter
@Setter
public class CourseStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int month;
    private int year;
    private int totalRegistrations;
    private float totalEarnings;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;
}
