package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.CourseDTO;
import com.vitube.online_learning.dto.request.CourseCreattionRequest;
import com.vitube.online_learning.entity.Objective;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.entity.Course;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, LessonMapper.class})
public interface CourseMapper {
    CourseDTO toDto(Course course);

    Course dtoToEntity(CourseDTO dto);


    @Mapping(target = "objectives", ignore = true)
    Course createRequestToEntity(CourseCreattionRequest request);


}
