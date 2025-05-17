package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.CourseStatisticResponse;
import com.vitube.online_learning.dto.response.StatisticResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Register;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final UserRepository userRepository;


    public StatisticResponse getCourseStatistic(String instructorId){
        StatisticResponse response = new StatisticResponse();
        List<CourseStatisticResponse> courseStatisticResponses = new ArrayList<>();
        long totalRegistrations = 0;
        float totalIncome = 0;
        int totalCourses = 0;

        User instructor = userRepository.findById(instructorId).orElse(null);
        if (instructor == null) {
            return null;
        }

        Set<Course> courses = instructor.getCourses();
        totalCourses = courses.size();
        if (courses == null || courses.isEmpty()) {
            return null;
        }

        for (Course course : courses) {
            CourseStatisticResponse result = new CourseStatisticResponse();
            result.setTitle(course.getTitle());

            result.setTotalRegistrations(course.getRegisters().size());
            float totalEarnings = 0;
            for (Register register : course.getRegisters()) {
                totalEarnings += register.getPrice();
            }
            result.setTotalEarnings(totalEarnings);
            totalRegistrations += result.getTotalRegistrations();
            totalIncome += result.getTotalEarnings();


            courseStatisticResponses.add(result);
        }
        response.setCourseStats(courseStatisticResponses);
        response.setTotalCourses(totalCourses);
        response.setTotalRegistrations(totalRegistrations);
        response.setTotalIncome(totalIncome);
        return response;
    }


}
