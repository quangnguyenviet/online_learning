package com.vitube.online_learning.dto.response;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String username;
    private Date dob;
    private String bankName;
    private String accountNumber;
    private String accountName;
    private String role;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
