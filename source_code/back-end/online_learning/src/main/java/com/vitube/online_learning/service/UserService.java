package com.vitube.online_learning.service;

import java.util.List;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.User;

public interface UserService {
    User userRequestToUser(UserRequest user);

    UserResponse userToUserREsponse(User user);

    UserResponse createUser(UserRequest request);

    void deleteUser(String id);

    List<UserResponse> getAllUsers();

    UserResponse getMyInfo();
}
