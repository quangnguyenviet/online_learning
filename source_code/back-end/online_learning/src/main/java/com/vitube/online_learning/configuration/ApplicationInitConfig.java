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
        return args -> {
            // Tạo và lưu các vai trò mặc định (ADMIN, USER và INSTRUCTOR)
            Role adminRole = Role.builder().name(RoleEnum.ADMIN.name()).build();
            Role userRole = Role.builder().name(RoleEnum.USER.name()).build();
            Role instructorRole = Role.builder().name(RoleEnum.INSTRUCTOR.name()).build();
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            roleRepository.save(instructorRole);

            // Kiểm tra xem tài khoản admin đã tồn tại hay chưa
            if (userRepository.findByUsername("admin") == null) {
                // Tạo và lưu tài khoản admin với vai trò ADMIN
                Role role = new Role();
                role.setName(RoleEnum.ADMIN.name());
                User adminAccount = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin")) // Mã hóa mật khẩu
                        .role(role)
                        .build();

                userRepository.save(adminAccount);
                log.info("Admin user created"); // Ghi log khi tạo tài khoản admin
            }
        };
    }
}