package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;

import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonResponse lessonToLessonResponse(Lesson lesson);
}
