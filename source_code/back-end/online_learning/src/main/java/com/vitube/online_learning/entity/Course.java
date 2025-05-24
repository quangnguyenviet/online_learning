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

    private String imageUrl;

    private Boolean published;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    private float price;

    private int discount;

    public long getNewPrice() {
        return (long) (price * ((100 - discount) * 1.0 / 100));
    }

    @OneToMany(mappedBy = "course")
    private Set<Register> registers;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Lesson> lessons;

    @OneToMany(mappedBy = "course")
    private Set<Order> orders;

    @OneToMany(mappedBy = "course")
    private Set<LearnWhat> learnWhats;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String longDesc; // → TEXT

    private String shortDesc;

    @OneToMany(mappedBy = "course")
    private Set<Require> requires;


}
