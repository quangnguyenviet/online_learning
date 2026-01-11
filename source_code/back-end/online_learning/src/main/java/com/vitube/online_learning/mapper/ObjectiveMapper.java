package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.ObjectiveDTO;
import com.vitube.online_learning.entity.Objective;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ObjectiveMapper {

    ObjectiveDTO toDto(Objective objective);

}
