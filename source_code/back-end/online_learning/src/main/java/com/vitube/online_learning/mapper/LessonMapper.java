package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.LessonDTO;
import org.mapstruct.*;

import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonResponse lessonToLessonResponse(Lesson lesson);

    LessonDTO entityToDto(Lesson lesson);

    @BeanMapping(nullValuePropertyMappingStrategy  = NullValuePropertyMappingStrategy.IGNORE)
    void dtoToEntity(LessonDTO lessonDTO, @MappingTarget Lesson lesson);
}
