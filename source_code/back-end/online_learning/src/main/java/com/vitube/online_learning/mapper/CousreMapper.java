package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CousreMapper {
    CourseResponse courseToCourseResponse(Course course);
}
