package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.mapper.CousreMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CousreMapper cousreMapper;

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .instructor(instructor)
                .price(request.getPrice())
                .build();

        Course saved = courseRepository.save(course);

        return CourseResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .instructorId(instructor.getId())
                .price(saved.getPrice())
                .build();
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        return null;
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        return null;
    }

    @Override
    public void deleteCourse(Long id) {

    }

    @Override
    public List<CourseResponse> getAllCourse() {
        List<CourseResponse> responseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            User instructor = course.getInstructor();
            responseList.add(CourseResponse.builder()
                   .id(course.getId())
                   .title(course.getTitle())
                   .instructorId(instructor.getId())
                   .price(course.getPrice())
                   .build());
        });
        return responseList;
    }
}
