package com.vitube.online_learning.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "instructor_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "statistic_id", referencedColumnName = "id", unique = true, nullable = false)
    private InstructorStatic statistic;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
