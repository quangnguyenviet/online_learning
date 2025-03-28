package com.vitube.online_learning.dto.response;

import java.util.Date;

import lombok.Data;

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
