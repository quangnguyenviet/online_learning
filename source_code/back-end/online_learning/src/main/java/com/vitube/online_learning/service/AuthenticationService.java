package com.vitube.online_learning.service;

import com.nimbusds.jose.JOSEException;
import com.vitube.online_learning.dto.request.AuthenticationRequest;
import com.vitube.online_learning.dto.request.IntrospectRequest;
import com.vitube.online_learning.dto.response.AuthenticationResponse;
import com.vitube.online_learning.dto.response.IntrospectRespone;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectRespone introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
