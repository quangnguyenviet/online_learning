package com.vitube.online_learning.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
