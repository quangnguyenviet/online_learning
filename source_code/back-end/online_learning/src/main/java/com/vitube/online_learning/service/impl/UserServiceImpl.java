package com.vitube.online_learning.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vitube.online_learning.dto.response.RoleResponse;
import com.vitube.online_learning.entity.Role;
import com.vitube.online_learning.mapper.RoleMapper;
import com.vitube.online_learning.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User userRequestToUser(UserRequest user) {
        User newUser = userMapper.userRequestToUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        String role = user.getRole();
        if (role != null) {
            Role role1 = roleRepository.getById(role);
            if (role1 != null) {
                Set<Role> roles = new HashSet<>();
                roles.add(role1);
                newUser.setRoles(roles);
            }
        }

        return newUser;
    }

    @Override
    public UserResponse userToUserREsponse(User user) {
        UserResponse userResponse = userMapper.userToUserResponse(user);
        Set<Role> roles = user.getRoles();
        List<RoleResponse> roleResponses = new ArrayList<RoleResponse>();
        for (Role role : roles) {
            RoleResponse roleResponse = roleMapper.roleToRoleResponse(role);
            roleResponses.add(roleResponse);
        }
        userResponse.setRole(roleResponses);
        return userResponse;

    }

    @Override
    public UserResponse createUser(UserRequest request) {
        User user = userRequestToUser(request);
        userRepository.save(user);
        return userToUserREsponse(user);
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
            userResponses.add(userToUserREsponse(user));
        }
        return userResponses;
    }

    @Override
    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return userToUserREsponse(user);
    }
}
