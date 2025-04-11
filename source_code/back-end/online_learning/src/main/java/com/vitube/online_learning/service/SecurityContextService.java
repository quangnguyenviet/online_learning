package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityContextService {
    private final UserRepository userRepository;

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getUser(){

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();


        User user = userRepository.findByUsername(userName);
        return user;
    }
}
