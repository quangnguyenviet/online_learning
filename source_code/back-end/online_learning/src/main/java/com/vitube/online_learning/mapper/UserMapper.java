package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse userToUserResponse(User user);

    User userRequestToUser(UserRequest user);
}
