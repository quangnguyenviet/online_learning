package com.vitube.online_learning.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private List<String> roles;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
