package com.vitube.online_learning.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.experimental.NonFinal;

/**
 * Lớp cấu hình bảo mật cho ứng dụng.
 * Bao gồm các thiết lập liên quan đến xác thực, phân quyền, CORS, và mã hóa mật khẩu.
 */
@Configuration
@EnableWebSecurity // Kích hoạt bảo mật web
@EnableMethodSecurity // Kích hoạt bảo mật ở cấp độ phương thức
public class SecurityConfig {

    // Các endpoint công khai cho phương thức POST
    private final String[] PUBLIC_ENPOINTS = {
            "/users", "/auth/login", "/auth/introspect", "/auth/logout", "/auth/refresh-token", "/zalopay/callback"
    };

    // Các endpoint công khai cho phương thức GET
    private final String[] PUBLIC_GET = {
            "/courses/*", "/courses", "/courses/{id}", "/courses/free", "/instructor-require/{courseId}",
    };

    // Khóa ký JWT được lấy từ file cấu hình
    @Value("${jwt.singerKey}")
    @NonFinal
    private String SIGNER_KEY;

    // Bộ giải mã JWT tùy chỉnh
    @Autowired
    @Lazy
    private MyJwtDecoder myJwtDecoder;

    /**
     * Cấu hình chuỗi bộ lọc bảo mật.
     * Thiết lập các quy tắc phân quyền, xử lý token JWT, và tắt CSRF.
     *
     * @param http Đối tượng HttpSecurity để cấu hình.
     * @return Chuỗi bộ lọc bảo mật đã được cấu hình.
     * @throws Exception Nếu xảy ra lỗi trong quá trình cấu hình.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).authorizeHttpRequests(request -> {
            request.requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS)
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, PUBLIC_GET)
                    .permitAll()
                    .anyRequest()
                    .authenticated();
        });

        http.oauth2ResourceServer(oauth2 -> {
            oauth2.jwt(jwt -> {
                        jwt.decoder(myJwtDecoder); // Cấu hình bộ giải mã cho token JWT
                    })
                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        });

        http.csrf(request -> {
            request.disable(); // Tắt CSRF
        });

        return http.build();
    }

    /**
     * Cấu hình CORS để cho phép các yêu cầu từ frontend.
     *
     * @return Đối tượng CorsConfigurationSource đã được cấu hình.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Chỉ định Frontend cụ thể
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Cho phép gửi Cookie / Authorization header

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Cấu hình bộ mã hóa mật khẩu.
     *
     * @return Đối tượng PasswordEncoder sử dụng thuật toán BCrypt.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}