package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);
    CourseResponse getCourseById(String id);
    CourseResponse updateCourse(String id, CourseRequest request);
    void deleteCourse(String id);
    List<CourseResponse> getAllCourse();
    List<CourseResponse> getFreeCourse();
    List<CourseResponse> getPlusCourse();
    List<CourseResponse> getLearningCourses();
    List<CourseResponse> getCoursesOfInstructor(String instructorId);
    List<CourseResponse> getMyCourses();


}
