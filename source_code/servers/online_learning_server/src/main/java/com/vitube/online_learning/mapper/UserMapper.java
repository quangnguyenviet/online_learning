package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.UserDTO;
import com.vitube.online_learning.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User dtoToUser(UserDTO userDTO);
}
