package com.vitube.online_learning.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Interface cung cấp các phương thức liên quan đến ngữ cảnh bảo mật.
 */
public interface SecurityContextService {

    /**
     * Lấy tên người dùng từ ngữ cảnh bảo mật hiện tại.
     *
     * @return Tên người dùng.
     */
    String getUsername();

    /**
     * Lấy thông tin người dùng từ ngữ cảnh bảo mật hiện tại.
     *
     * @return Đối tượng người dùng.
     */
    User getUser();
}