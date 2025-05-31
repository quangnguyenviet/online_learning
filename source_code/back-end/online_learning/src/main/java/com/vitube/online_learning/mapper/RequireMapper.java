package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;

import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.entity.Require;

@Mapper(componentModel = "spring")
public interface RequireMapper {

    RequireResponse requireToRequireResponse(Require require);
}
