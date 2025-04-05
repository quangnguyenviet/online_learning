package com.vitube.online_learning.entity;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "invalidToken")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvalidToken {
    @Id
    private String id;

    private Date expiration;
}
