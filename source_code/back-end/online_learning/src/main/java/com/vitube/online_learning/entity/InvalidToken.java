package com.vitube.online_learning.entity;

import java.util.Date;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invalidToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvalidToken {
    @Id
    private String id;

    private Date expiration;
}
