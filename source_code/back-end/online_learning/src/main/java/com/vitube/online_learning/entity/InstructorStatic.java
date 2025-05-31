package com.vitube.online_learning.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "instructor_statistic")
@Getter
@Setter
public class InstructorStatic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int month;
    private int year;
    private int totalRegistrations;
    private float totalEarnings;

    @ManyToOne
    private User instructor;

    //    @ManyToOne
    //    @JoinColumn(name = "instructor_id")
    //    private User instructor;
}
