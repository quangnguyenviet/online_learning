package com.vitube.online_learning.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "permission")
@Data
public class Permission {
    @Id
    private String name;

    private String description;
}
