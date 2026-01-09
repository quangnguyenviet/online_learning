package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.User;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User dtoToUser(UserDTO userDTO);
}
