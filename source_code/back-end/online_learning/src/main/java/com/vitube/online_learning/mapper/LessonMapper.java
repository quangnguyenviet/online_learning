package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface LessonMapper {
    LessonResponse lessonToLessonResponse(Lesson lesson);
}
