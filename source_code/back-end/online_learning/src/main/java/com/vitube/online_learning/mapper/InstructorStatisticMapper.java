package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.response.InstructorStatisticResponse;
import com.vitube.online_learning.entity.InstructorStatic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstructorStatisticMapper {

    InstructorStatisticResponse entityToResponse(InstructorStatic instructorStatic);
}
