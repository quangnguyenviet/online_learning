package com.vitube.online_learning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.mapper.UserMapper;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest request) {
        User newUser = userMapper.userRequestToUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);
        return userMapper.userToUserResponse(newUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<UserResponse>();
        for (User user : users) {
            userResponses.add(userMapper.userToUserResponse(user));
        }
        return userResponses;
    }

    @Override
    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return userMapper.userToUserResponse(user);
    }
}
