package com.vitube.online_learning.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String username;
    private Date dob;
}
