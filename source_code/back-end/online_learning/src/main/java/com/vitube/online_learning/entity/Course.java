package com.vitube.online_learning.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "course")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    private double price;

}
