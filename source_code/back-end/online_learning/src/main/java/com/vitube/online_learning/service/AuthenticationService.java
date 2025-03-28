package com.vitube.online_learning.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.vitube.online_learning.dto.request.AuthenticationRequest;
import com.vitube.online_learning.dto.request.IntrospectRequest;
import com.vitube.online_learning.dto.request.LogoutRequest;
import com.vitube.online_learning.dto.request.RefreshRequest;
import com.vitube.online_learning.dto.response.AuthenticationResponse;
import com.vitube.online_learning.dto.response.IntrospectRespone;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectRespone introspect(IntrospectRequest request) throws JOSEException, ParseException;

    Object logout(LogoutRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);
}
