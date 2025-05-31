//package com.vitube.online_learning.entity;
//
//import jakarta.persistence.*;
//
//import lombok.*;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "orders")
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String id;
//
//    @ManyToOne
//    @JoinColumn(name = "course_id")
//    private Course course;
//
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private User student;
//
//    private String status;
//}
