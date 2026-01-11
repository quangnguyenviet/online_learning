package com.vitube.online_learning.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.vitube.online_learning.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vitube.online_learning.dto.request.AuthenticationRequest;
import com.vitube.online_learning.dto.request.IntrospectRequest;
import com.vitube.online_learning.dto.request.LogoutRequest;
import com.vitube.online_learning.dto.request.RefreshRequest;
import com.vitube.online_learning.dto.response.AuthenticationResponse;
import com.vitube.online_learning.dto.response.IntrospectRespone;
import com.vitube.online_learning.entity.InvalidToken;
import com.vitube.online_learning.entity.Role;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.enums.RoleEnum;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.repository.InvalidTokenRepository;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

/**
 * Lớp triển khai các phương thức liên quan đến xác thực.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final InvalidTokenRepository invalidTokenRepository;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    @Value("${jwt.singerKey}")
    @NonFinal
    String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    @NonFinal
    long VALID_DURATION;

    @Value("${jwt.refresh-duration}")
    @NonFinal
    long REFRESH_DURATION;

    /**
     * Xác thực người dùng dựa trên thông tin đăng nhập.
     *
     * @param request Yêu cầu xác thực.
     * @return Phản hồi xác thực.
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // log authority of user
        

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXIST)
        );

        // verify password
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        // generate token
        String token = jwtService.generateToken(user);
        // extract rolea as a list of string
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .toList();
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(token)
                .roles(roles)
                .build();
        return response;

    }

    /**
     * Kiểm tra tính hợp lệ của token.
     *
     * @param request Yêu cầu introspect.
     * @return Phản hồi introspect.
     * @throws JOSEException Lỗi liên quan đến xử lý token.
     * @throws ParseException Lỗi phân tích token.
     */
    @Override
    public IntrospectRespone introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        boolean valid = verified && expiration.after(new Date());
        try {
            InvalidToken invalidToken = invalidTokenRepository
                    .findById(signedJWT.getJWTClaimsSet().getJWTID())
                    .get();
            valid = false;
        } catch (NoSuchElementException e) {
            // token không tồn tại trong bảng invalidToken
        }

        return IntrospectRespone.builder().valid(valid).build();
    }

    /**
     * Đăng xuất người dùng bằng cách vô hiệu hóa token.
     *
     * @param request Yêu cầu đăng xuất.
     * @return Đối tượng phản hồi (null).
     */
    @Override
    public Object logout(LogoutRequest request) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.getToken()); // có thể xảy ra ParseException
            String jtid = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiration = new Date(signedJWT
                    .getJWTClaimsSet()
                    .getIssueTime()
                    .toInstant()
                    .plus(REFRESH_DURATION, ChronoUnit.SECONDS)
                    .toEpochMilli());

            if (!expiration.before(new Date())) {
                InvalidToken invalidToken =
                        InvalidToken.builder().id(jtid).expiration(expiration).build();
                invalidTokenRepository.save(invalidToken);
            }

        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        return null;
    }

    /**
     * Làm mới token cho người dùng.
     *
     * @param request Yêu cầu làm mới token.
     * @return Phản hồi xác thực với token mới.
     */
    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
            SignedJWT signedJWT = jwtService.verifyToken(request.getToken(), true);

            String jtid = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            String email = signedJWT.getJWTClaimsSet().getSubject();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

            InvalidToken invalidToken =
                    InvalidToken.builder().id(jtid).expiration(expiration).build();
            invalidTokenRepository.save(invalidToken);

            String newToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(newToken)
                    .build();


    }


}