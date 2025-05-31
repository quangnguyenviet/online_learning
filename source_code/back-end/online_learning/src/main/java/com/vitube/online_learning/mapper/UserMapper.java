package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    UserResponse userToUserResponse(User user);

    @Mapping(target = "role", ignore = true)
    User userRequestToUser(UserRequest user);
}
