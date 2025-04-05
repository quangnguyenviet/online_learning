package com.vitube.online_learning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    private double price;

    private double discount;

    public double getNewPrice() {
        return price * (1 - discount / 100);
    }

    @OneToMany(mappedBy = "course")
    private Set<Register> registers;

}
