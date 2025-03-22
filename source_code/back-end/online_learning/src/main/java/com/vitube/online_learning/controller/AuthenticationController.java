package com.vitube.online_learning.controller;

import com.nimbusds.jose.JOSEException;
import com.vitube.online_learning.dto.request.AuthenticationRequest;
import com.vitube.online_learning.dto.request.IntrospectRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<?> authenticate(@RequestBody AuthenticationRequest request){

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

}
