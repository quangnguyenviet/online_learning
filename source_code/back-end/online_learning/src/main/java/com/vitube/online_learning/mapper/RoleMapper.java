package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.RoleDTO;
import com.vitube.online_learning.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role dtoToRole(RoleDTO dto);
    RoleDTO roleToDto(Role role);
}
