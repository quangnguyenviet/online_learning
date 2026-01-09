package com.vitube.online_learning.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vitube.online_learning.dto.UserDTO;
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
     * Tạo người dùng mới.
     *
     * @param request Yêu cầu tạo người dùng.
     * @return Đối tượng phản hồi người dùng.
     */
    @Override
    public UserDTO createUser(UserDTO request) {
        User user = userMapper.dtoToUser(request);
        if (!ValidateUtil.isValidEmail(user.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }

        if (!ValidateUtil.isValidPassword(user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }
        userRepository.save(user);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return userDTO;
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
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream()
                .map(user -> userMapper.userToUserDTO(user))
                .toList();

        return userDTOS;
    }

    /**
     * Lấy thông tin của người dùng hiện tại.
     *
     * @return Đối tượng phản hồi người dùng.
     */
    @Override
    public UserDTO getMyInfo() {
        User user = getCurrentUser();
        return userMapper.userToUserDTO(user);
    }

    @Override
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        return user;
    }
}