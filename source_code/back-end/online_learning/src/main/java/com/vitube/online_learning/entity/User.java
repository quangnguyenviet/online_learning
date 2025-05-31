package com.vitube.online_learning.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String username;

    @Column
    private Date dob;

    private String bankName;
    private String accountNumber;
    private String accountName;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "instructor")
    private Set<Course> courses;

    @OneToMany(mappedBy = "student")
    private Set<Register> registers;

    @OneToMany(mappedBy = "instructor")
    private List<InstructorStatic> instructorStatics;

//    @OneToMany(mappedBy = "student")
//    private Set<Order> orders;

    //    @OneToMany(mappedBy = "instructor")
    //    private Set<InstructorStatic> instructorStatics;
}
