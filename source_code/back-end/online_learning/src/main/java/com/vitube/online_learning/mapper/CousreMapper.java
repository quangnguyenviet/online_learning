package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CousreMapper {
    @Mapping(target = "instructorId", ignore = true)
    CourseResponse courseToCourseResponse(Course course);
}
