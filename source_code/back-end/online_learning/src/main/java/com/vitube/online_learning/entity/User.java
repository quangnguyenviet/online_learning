package com.vitube.online_learning.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "user")
@Data
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

    @ManyToMany
    private Set<Role> roles;
}
