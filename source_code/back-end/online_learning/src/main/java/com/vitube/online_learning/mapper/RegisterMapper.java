package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.entity.Register;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    // Define your mapping methods here
    // For example:
    // RegisterDto toDto(Register register);
    // Register toEntity(RegisterDto registerDto);
    Register toEntity(RegisterRequest registerRequest);
}
