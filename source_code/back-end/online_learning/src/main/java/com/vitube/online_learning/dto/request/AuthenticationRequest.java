package com.vitube.online_learning.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
    private String role;
}
