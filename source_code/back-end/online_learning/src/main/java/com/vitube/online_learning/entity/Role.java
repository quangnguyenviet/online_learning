package com.vitube.online_learning.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    private String name;

    private String description;

    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
