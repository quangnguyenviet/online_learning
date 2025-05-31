package com.vitube.online_learning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.entity.Course;

public interface CourseService {
    CourseResponse courseToCourseResponse(Course course, int type);

    CourseResponse createCourse(CourseRequest request, MultipartFile image);

    CourseResponse getCourseById(String id);

    CourseResponse updateCourse(String id, CourseRequest request);

    void deleteCourse(String id);

    List<CourseResponse> getCourses(String type, String query);

    List<CourseResponse> getFreeCourse();

    List<CourseResponse> getPlusCourse();

    List<CourseResponse> getLearningCourses();

    List<CourseResponse> getCoursesOfInstructor(String instructorId);

    List<CourseResponse> getMyCourses();

    void setPrice(String courseId, Float price);
}
