package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Lớp triển khai các phương thức liên quan đến ngữ cảnh bảo mật.
 */
@Service
@RequiredArgsConstructor
public class SecurityContextServiceImpl implements SecurityContextService {
    private final UserRepository userRepository;


}