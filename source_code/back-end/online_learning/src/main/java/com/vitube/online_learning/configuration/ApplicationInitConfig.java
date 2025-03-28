package com.vitube.online_learning.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vitube.online_learning.entity.Role;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.enums.RoleEnum;
import com.vitube.online_learning.repository.RoleRepository;
import com.vitube.online_learning.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            Role adminRole = Role.builder().name(RoleEnum.ADMIN.name()).build();
            Role userRole = Role.builder().name(RoleEnum.USER.name()).build();
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            if (userRepository.findByUsername("admin") == null) {
                Set<Role> roles = new HashSet<Role>();
                roles.add(adminRole);

                User adminAccount = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(adminAccount);
                log.info("Admin user created");
            }
        };
    }
}
