package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.LessonDTO;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Lesson;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonResponse lessonToLessonResponse(Lesson lesson);

    LessonDTO entityToDto(Lesson lesson);

    @BeanMapping(nullValuePropertyMappingStrategy  = NullValuePropertyMappingStrategy.IGNORE)
    void dtoToEntity(LessonDTO lessonDTO, @MappingTarget Lesson lesson);
}
