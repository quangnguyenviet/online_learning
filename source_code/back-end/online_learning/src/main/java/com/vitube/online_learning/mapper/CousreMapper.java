package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.entity.Course;

@Mapper(componentModel = "spring")
public interface CousreMapper {
    @Mapping(target = "instructorId", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    CourseResponse courseToCourseResponse(Course course);

    @Mapping(target = "instructor", ignore = true)
    Course requestToCourse(CourseRequest request);
}
