package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.LessonProgressDTO;
import com.vitube.online_learning.entity.LessonProgress;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LessonProgressMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    LessonProgressDTO entityToDto(LessonProgress lessonProgress);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(LessonProgressDTO dto, @MappingTarget LessonProgress entity);
}
