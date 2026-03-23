package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.entity.Register;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    // Define your mapping methods here
    // For example:
    // RegisterDto toDto(Register register);
    // Register toEntity(RegisterDto registerDto);
    Register toEntity(RegisterRequest registerRequest);
}
