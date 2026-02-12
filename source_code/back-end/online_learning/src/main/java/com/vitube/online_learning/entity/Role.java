package com.vitube.online_learning.entity;

import com.vitube.online_learning.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
