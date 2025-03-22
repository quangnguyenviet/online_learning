package com.vitube.online_learning.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String username;
    private Date dob;
}
