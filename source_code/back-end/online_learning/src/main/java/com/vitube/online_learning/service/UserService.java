package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    void deleteUser(String id);
    List<UserResponse> getAllUsers();
}
