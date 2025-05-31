package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;

import com.vitube.online_learning.dto.response.RoleResponse;
import com.vitube.online_learning.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse roleToRoleResponse(Role role);
}
