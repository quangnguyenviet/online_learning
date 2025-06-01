package com.vitube.online_learning.configuration;

import java.text.ParseException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.vitube.online_learning.dto.request.IntrospectRequest;
import com.vitube.online_learning.service.AuthenticationService;

import lombok.experimental.NonFinal;

/**
 * Triển khai tùy chỉnh của JwtDecoder để giải mã và xác thực token JWT.
 * Lớp này sử dụng dịch vụ xác thực bên ngoài để kiểm tra token và thư viện Nimbus để giải mã token.
 */
@Component
public class MyJwtDecoder implements JwtDecoder {

    // Lấy khóa ký JWT từ file cấu hình
    @Value("${jwt.singerKey}")
    @NonFinal
    private String signerKey;

    // Dịch vụ xác thực được sử dụng để kiểm tra token
    @Autowired
    private AuthenticationService authenticationService;

    // Đối tượng NimbusJwtDecoder để giải mã token JWT
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    /**
     * Giải mã và xác thực token JWT được cung cấp.
     * Thực hiện kiểm tra token bằng dịch vụ xác thực và giải mã token bằng thư viện Nimbus.
     *
     * @param token Token JWT cần giải mã.
     * @return Đối tượng Jwt đã được giải mã.
     * @throws JwtException Nếu token không hợp lệ hoặc giải mã thất bại.
     */
    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            // Kiểm tra token bằng dịch vụ xác thực
            var response = authenticationService.introspect(
                    IntrospectRequest.builder().token(token).build());

            // Ném ngoại lệ nếu token không hợp lệ
            if (!response.isValid()) throw new JwtException("Token invalid");
        } catch (JOSEException | ParseException e) {
            // Xử lý ngoại lệ trong quá trình kiểm tra
            throw new JwtException(e.getMessage());
        }

        // Tạo khóa bí mật để giải mã token
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");

        // Khởi tạo NimbusJwtDecoder với khóa bí mật và thuật toán
        nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();

        // Giải mã và trả về token JWT
        return nimbusJwtDecoder.decode(token);
    }
}