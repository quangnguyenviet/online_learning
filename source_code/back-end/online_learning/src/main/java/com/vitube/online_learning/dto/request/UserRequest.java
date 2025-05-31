package com.vitube.online_learning.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class UserRequest extends BaseRequest{
    private String email;
    private String password;
    private String password2;
    private String firstName;
    private String lastName;
    private String username;
    private String bankName;
    private String accountNumber;
    private String accountName;
    private Date dob;
    private String role = "USER";
}
