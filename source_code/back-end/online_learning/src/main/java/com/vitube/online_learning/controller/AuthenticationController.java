package com.vitube.online_learning.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.vitube.online_learning.dto.request.AuthenticationRequest;
import com.vitube.online_learning.dto.request.IntrospectRequest;
import com.vitube.online_learning.dto.request.LogoutRequest;
import com.vitube.online_learning.dto.request.RefreshRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.AuthenticationService;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<?> authenticate(@RequestBody AuthenticationRequest request) {

        return ApiResponse.builder()
                .status(1000)
                .data(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<?> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.builder()
                .status(1000)
                .data(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestBody LogoutRequest request) {
        authenticationService.logout(request);
        return ApiResponse.builder().status(1000).build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<?> refreshtoken(@RequestBody RefreshRequest request) {

        return ApiResponse.builder()
                .status(1000)
                .data(authenticationService.refreshToken(request))
                .build();
    }
}
