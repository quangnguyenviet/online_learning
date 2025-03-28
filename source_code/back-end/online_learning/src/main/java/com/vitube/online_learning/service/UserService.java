package com.vitube.online_learning.service;

import java.util.List;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);

    void deleteUser(String id);

    List<UserResponse> getAllUsers();

    UserResponse getMyInfo();
}
