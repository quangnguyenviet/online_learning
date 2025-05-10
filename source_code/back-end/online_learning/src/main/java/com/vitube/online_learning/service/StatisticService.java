package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.CourseStatisticResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final UserRepository userRepository;


    public List<CourseStatisticResponse> getCourseStatistic(String instructorId){
        User instructor = userRepository.findById(instructorId).orElse(null);
        if (instructor == null) {
            return null;
        }

        Set<Course> courses = instructor.getCourses();
        if (courses == null || courses.isEmpty()) {
            return null;
        }



        return null;


    }
}
