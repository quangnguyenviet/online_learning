package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(com.vitube.online_learning.entity.Category category);
}
