package com.vitube.online_learning.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UserCreationRequest {
    private String email;
    private String password;
    private List<String> roles;
}
