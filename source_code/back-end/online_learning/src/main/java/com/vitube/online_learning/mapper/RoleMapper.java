package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.response.RoleResponse;
import com.vitube.online_learning.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse roleToRoleResponse(Role role);
}
