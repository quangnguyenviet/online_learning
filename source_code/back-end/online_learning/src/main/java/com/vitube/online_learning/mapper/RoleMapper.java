package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.RoleDTO;
import org.mapstruct.Mapper;

import com.vitube.online_learning.dto.response.RoleResponse;
import com.vitube.online_learning.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role dtoToRole(RoleDTO dto);
    RoleDTO roleToDto(Role role);
}
