package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Lớp triển khai các phương thức liên quan đến ngữ cảnh bảo mật.
 */
@Service
@RequiredArgsConstructor
public class SecurityContextServiceImpl implements SecurityContextService {
    private final UserRepository userRepository;

    /**
     * Lấy tên người dùng từ ngữ cảnh bảo mật.
     *
     * @return Tên người dùng.
     */
    @Override
    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Lấy thông tin người dùng từ ngữ cảnh bảo mật.
     *
     * @return Đối tượng người dùng.
     */
    @Override
    public User getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Tìm người dùng theo tên đăng nhập
        User user = userRepository.findByUsername(userName);
        return user;
    }
}