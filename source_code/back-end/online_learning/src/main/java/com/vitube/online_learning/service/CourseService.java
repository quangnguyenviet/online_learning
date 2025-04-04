package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);
    CourseResponse getCourseById(Long id);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);
    List<CourseResponse> getAllCourse();
}
