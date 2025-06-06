package com.vitube.online_learning.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cloudinary.api.exceptions.ApiException;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.utils.ValidateUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.RoleResponse;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.Role;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.mapper.RoleMapper;
import com.vitube.online_learning.mapper.UserMapper;
import com.vitube.online_learning.repository.RoleRepository;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Lớp triển khai các phương thức liên quan đến người dùng.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Chuyển đổi đối tượng UserRequest thành User.
     *
     * @param user Yêu cầu người dùng.
     * @return Đối tượng người dùng.
     */
    @Override
    public User userRequestToUser(UserRequest user) {
        User newUser = userMapper.userRequestToUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        String role = user.getRole();
        if (role != null) {
            Role role1 = roleRepository.getById(role);
            if (role1 != null) {
                newUser.setRole(role1);
            }
        } else {
            Role userRole = roleRepository.getReferenceById("USER");
            newUser.setRole(userRole);
        }

        return newUser;
    }

    /**
     * Chuyển đổi đối tượng User thành UserResponse.
     *
     * @param user Đối tượng người dùng.
     * @return Đối tượng phản hồi người dùng.
     */
    @Override
    public UserResponse userToUserREsponse(User user) {
        UserResponse userResponse = userMapper.userToUserResponse(user);
        userResponse.setRole(user.getRole().getName());

        return userResponse;
    }

    /**
     * Tạo người dùng mới.
     *
     * @param request Yêu cầu tạo người dùng.
     * @return Đối tượng phản hồi người dùng.
     */
    @Override
    public UserResponse createUser(UserRequest request) {
        User user = userRequestToUser(request);
        if (!ValidateUtil.isValidEmail(user.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }
        if (!ValidateUtil.isValidUsername(user.getUsername())) {
            throw new AppException(ErrorCode.INVALID_USERNAME);
        }
        if (!ValidateUtil.isValidPassword(user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXIST);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }
        userRepository.save(user);
        return userToUserREsponse(user);
    }

    /**
     * Xóa người dùng theo ID.
     *
     * @param id ID của người dùng.
     */
    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    /**
     * Lấy danh sách tất cả người dùng.
     *
     * @return Danh sách phản hồi người dùng.
     */
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<UserResponse>();
        for (User user : users) {
            userResponses.add(userToUserREsponse(user));
        }
        return userResponses;
    }

    /**
     * Lấy thông tin của người dùng hiện tại.
     *
     * @return Đối tượng phản hồi người dùng.
     */
    @Override
    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return userToUserREsponse(user);
    }
}