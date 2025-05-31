package com.vitube.online_learning.entity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "course_requirement")
@Entity
public class Require {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
