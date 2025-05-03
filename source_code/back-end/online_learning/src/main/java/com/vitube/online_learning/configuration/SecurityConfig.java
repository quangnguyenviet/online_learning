package com.vitube.online_learning.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.experimental.NonFinal;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // optional
@EnableMethodSecurity // enable method security
public class SecurityConfig {

    private final String[] PUBLIC_ENPOINTS = {
        "/users", "/auth/login", "/auth/introspect", "/auth/logout", "/auth/refresh-token",
            "/zalopay/callback"
    };
    private final String[] PUBLIC_GET = {
            "/courses/*",
            "/courses",
            "/courses/{id}",
            "/courses/free",
            "/instructor-require/{courseId}",

    };

    @Value("${jwt.singerKey}")
    @NonFinal
    private String SIGNER_KEY;

    @Autowired
    @Lazy
    private MyJwtDecoder myJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> {
            request.requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS)
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, PUBLIC_GET).permitAll()
                    .requestMatchers("/users/**")
                    .hasAuthority("SCOPE_ADMIN")
                    .anyRequest()
                    .authenticated();
        });

        http.oauth2ResourceServer(oauth2 -> {
            oauth2.jwt(jwt -> {
                        jwt.decoder(myJwtDecoder); // config decoder for jwt token
                    })
                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        });

        http.csrf(request -> {
            request.disable(); // turn off csrf
        });

        return http.build();
    }

    //    @Bean
    //    JwtDecoder jwtDecoder(){
    //        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
    //        return NimbusJwtDecoder
    //                .withSecretKey(secretKeySpec)
    //                .macAlgorithm(MacAlgorithm.HS512)
    //                .build();
    //    }



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
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
