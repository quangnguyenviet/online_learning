package com.vitube.online_learning.configuration;

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

import lombok.experimental.NonFinal;

@Configuration
@EnableWebSecurity // optional
@EnableMethodSecurity // enable method security
public class SecurityConfig {

    private final String[] PUBLIC_ENPOINTS = {
        "/users", "/auth/login", "/auth/introspect", "/auth/logout", "/auth/refresh-token"
    };

    @Value("${jwt.singerKey}")
    @NonFinal
    private String SIGNER_KEY;

    @Autowired
    @Lazy
    private MyJwtDecoder myJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> {
            request.requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS)
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/users")
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
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
