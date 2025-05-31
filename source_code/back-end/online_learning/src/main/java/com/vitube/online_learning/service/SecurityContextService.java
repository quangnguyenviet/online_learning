package com.vitube.online_learning.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.UserRepository;

import lombok.RequiredArgsConstructor;

public interface SecurityContextService {

    String getUsername();

    User getUser();
}
