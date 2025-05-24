package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.CourseStatisticResponse;
import com.vitube.online_learning.dto.response.StatisticResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.InstructorStatic;
import com.vitube.online_learning.entity.Register;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.InstructorStatisticRepository;
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
    private final InstructorStatisticRepository instructorStatisticRepository;


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

    public List<InstructorStatic> getInstructorStatistic(String instructorId) {
        List<InstructorStatic> instructorStatics = instructorStatisticRepository.findByInstructorId(instructorId);
        int nowYear = java.time.LocalDate.now().getYear();
        int nowMonth = java.time.LocalDate.now().getMonthValue();
        for(int i = 1; i <= nowMonth; i++){
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
                instructorStatic.setInstructorId(instructorId);
                instructorStatics.add(instructorStatic);
            }
        }
        instructorStatics.sort((o1, o2) -> {
            return Integer.compare(o1.getMonth(), o2.getMonth());
        });
        return instructorStatics;
    }


}
