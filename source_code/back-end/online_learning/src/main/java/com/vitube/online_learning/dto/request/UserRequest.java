package com.vitube.online_learning.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String username;
    private Date dob;
}
