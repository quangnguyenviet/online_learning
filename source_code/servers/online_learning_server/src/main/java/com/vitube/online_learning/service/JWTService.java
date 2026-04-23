package com.vitube.online_learning.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.vitube.online_learning.entity.User;

import java.text.ParseException;

public interface JWTService {
    String generateToken(User user);
    SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException;
}
