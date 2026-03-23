package com.vitube.online_learning.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vitube.online_learning.entity.InvalidToken;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.repository.InvalidTokenRepository;
import com.vitube.online_learning.service.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTServiceImpl implements JWTService {
    private final InvalidTokenRepository invalidTokenRepository;

    @Value("${jwt.valid-duration}")
    private long VALID_DURATION;

    @Value("${jwt.singerKey}")
    String SIGNER_KEY;

    @Value("${jwt.refresh-duration}")
    long REFRESH_DURATION;

    @Override
    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("Online Learning")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .subject(user.getEmail())
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getRoles()
                        .stream()
                        .map(r -> r.getName())
                        .toList()
                )
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    @Override
    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = null;
            verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiration = (isRefresh)
                    ? (new Date(signedJWT
                    .getJWTClaimsSet()
                    .getIssueTime()
                    .toInstant()
                    .plus(REFRESH_DURATION, ChronoUnit.SECONDS)
                    .toEpochMilli()))
                    : (signedJWT.getJWTClaimsSet().getExpirationTime());

            var verified = signedJWT.verify(verifier);

            boolean valid = verified && expiration.after(new Date());

            InvalidToken invalidToken = invalidTokenRepository
                        .findById(signedJWT.getJWTClaimsSet().getJWTID())
                        .get();
            if (invalidToken != null) {
                valid = false;
            }

            if (!valid) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }

            return signedJWT;


    }

}
