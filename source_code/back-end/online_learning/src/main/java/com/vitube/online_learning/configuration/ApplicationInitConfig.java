package com.vitube.online_learning.configuration;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
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

    // Inject PasswordEncoder để mã hóa mật khẩu
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Inject RoleRepository để quản lý vai trò
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Định nghĩa bean ApplicationRunner.
     * Phương thức này chạy logic khởi tạo sau khi ứng dụng khởi động.
     * Tạo các vai trò mặc định và tài khoản admin nếu chưa tồn tại.
     */
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        log.info("inside applicationRunner: start application runner");
        return args -> {
            // generate roles (ADMIN, STUDENT và INSTRUCTOR)
            for (RoleEnum roleEnum : RoleEnum.values()) {
                roleRepository.findById(roleEnum)
                        .orElseGet(() ->
                                roleRepository.save(
                                        Role.builder()
                                                .name(roleEnum)
                                                .build()
                                )
                        );
                log.info("inside applicationRunner: create defefault roles");
            }


            // Kiểm tra xem tài khoản admin đã tồn tại hay chưa
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                // Tạo và lưu tài khoản admin với vai trò ADMIN
                List<Role> roles = roleRepository.findAll();

                User adminAccount = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin")) // Mã hóa mật khẩu
                        .roles(roles)
                        .build();

                userRepository.save(adminAccount);
                log.info("inside applicationRunner: Admin user created"); // Ghi log khi tạo tài khoản admin
            }
        };
    }
}