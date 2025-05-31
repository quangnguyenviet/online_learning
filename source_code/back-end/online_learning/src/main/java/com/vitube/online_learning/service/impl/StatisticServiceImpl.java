package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.response.CourseStatisticResponse;
import com.vitube.online_learning.dto.response.InstructorStatisticResponse;
import com.vitube.online_learning.dto.response.StatisticResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.InstructorStatic;
import com.vitube.online_learning.entity.Register;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.mapper.InstructorStatisticMapper;
import com.vitube.online_learning.repository.InstructorStatisticRepository;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final UserRepository userRepository;
    private final InstructorStatisticRepository instructorStatisticRepository;
    private final InstructorStatisticMapper instructorStatisticMapper;;

    public StatisticResponse getCourseStatistic(String instructorId) {
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

    public List<InstructorStatisticResponse> getInstructorStatistic(String instructorId) {
        User instructor = userRepository.findById(instructorId).orElse(null);
        List<InstructorStatic> instructorStatics = instructor.getInstructorStatics();
        int nowYear = java.time.LocalDate.now().getYear();
        int nowMonth = java.time.LocalDate.now().getMonthValue();
        List<InstructorStatisticResponse> instructorStatisticResponses = new ArrayList<>();
        for (int i = 1; i <= nowMonth; i++) {
            boolean isExist = false;
            for (InstructorStatic instructorStatic : instructorStatics) {
                if (instructorStatic.getYear() == nowYear && instructorStatic.getMonth() == i) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                InstructorStatic instructorStatic = new InstructorStatic();
                instructorStatic.setYear(nowYear);
                instructorStatic.setMonth(i);
                instructorStatic.setTotalRegistrations(0);
                instructorStatic.setTotalEarnings(0);
                instructorStatics.add(instructorStatic);
            }
        }

        for (InstructorStatic instructorStatic : instructorStatics) {
            InstructorStatisticResponse response = instructorStatisticMapper.entityToResponse(instructorStatic);
            instructorStatisticResponses.add(response);
        }

        Collections.sort(instructorStatisticResponses);
        return instructorStatisticResponses;
    }
}
