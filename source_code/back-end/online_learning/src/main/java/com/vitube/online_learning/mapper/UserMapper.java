package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.User;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    UserResponse userToUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    User userRequestToUser(UserRequest user);
}
