package com.vitube.online_learning.mapper;


import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.entity.Require;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequireMapper {

    RequireResponse requireToRequireResponse(Require require);
}
