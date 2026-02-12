package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.response.InstructorRegistrationChartResponse;
import com.vitube.online_learning.dto.response.InstructorStatsSummaryResponse;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.RegisterRepository;
import com.vitube.online_learning.repository.projection.RegistrationStats;
import com.vitube.online_learning.service.CourseService;
import com.vitube.online_learning.service.InstructorDashboardService;
import com.vitube.online_learning.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorDashboardServiceImpl implements InstructorDashboardService {
    private final CourseService courseService;
    private final RegisterRepository registerRepository;
    private final UserService userService;

    @Override
    public InstructorStatsSummaryResponse getInstructorDashboardSummary() {
        User instructor = userService.getCurrentUser();
        String instructorId = instructor.getId();

        long totalCourses = courseService.countCourseByInstructorId(instructorId);
        long publishedCourses = courseService.countPublishedCourseByInstructorId(instructorId);
        long totalStudents = courseService.countTotalStudentsByInstructorId(instructorId);
        long totalVideos = courseService.countTotalVideosByInstructorId(instructorId);
        BigDecimal totalEarnings = registerRepository.sumPriceByInstructorId(instructorId);
        if (totalEarnings == null) {
            totalEarnings = BigDecimal.ZERO;
        }
        Double averageRating = 0.0;

        InstructorStatsSummaryResponse response = InstructorStatsSummaryResponse.builder()
                .totalCourses(totalCourses)
                .publishedCourses(publishedCourses)
                .totalStudents(totalStudents)
                .totalVideos(totalVideos)
                .totalEarnings(totalEarnings)
                .averageRating(averageRating)
                .build();

        return response;
    }

    @Override
    public InstructorRegistrationChartResponse getRegistrationChart(String filter) {
        log.info("Inside getRegistrationChart() of InstructorDashboardServiceImpl");

        User instructor = userService.getCurrentUser();
        String instructorId = instructor.getId();

        List<RegistrationStats> list;
        List<String> labels = new ArrayList<>();
        List<Integer> totals = new ArrayList<>();
        LocalDate today = LocalDate.now();

        if ("week".equalsIgnoreCase(filter)) {
            list = registerRepository.countRegistrationsStatsByWeek(instructorId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            Map<LocalDate, Long> map = new HashMap<>();
            if (list != null) list.forEach(s -> map.put(s.getRegistrationDay(), s.getTotalRegistrations()));

            for (int i = 11; i >= 0; i--) {
                LocalDate date = today.minusWeeks(i);
                // Adjust to Monday to match repository logic
                LocalDate startOfWeek = date.minusDays(date.getDayOfWeek().getValue() - 1);
                labels.add(startOfWeek.format(formatter));
                totals.add(map.getOrDefault(startOfWeek, 0L).intValue());
            }
        } else if ("month".equalsIgnoreCase(filter)) {
            list = registerRepository.countRegistrationsStatsByMonth(instructorId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            Map<LocalDate, Long> map = new HashMap<>();
            if (list != null) list.forEach(s -> map.put(s.getRegistrationDay(), s.getTotalRegistrations()));

            for (int i = 11; i >= 0; i--) {
                LocalDate date = today.minusMonths(i);
                LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());
                labels.add(lastDayOfMonth.format(formatter));
                totals.add(map.getOrDefault(lastDayOfMonth, 0L).intValue());
            }
        } else {
            list = registerRepository.countRegistrationsStatsByDay(instructorId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            Map<LocalDate, Long> map = new HashMap<>();
            if (list != null) list.forEach(s -> map.put(s.getRegistrationDay(), s.getTotalRegistrations()));

            for (int i = 6; i >= 0; i--) {
                LocalDate day = today.minusDays(i);
                labels.add(day.format(formatter));
                totals.add(map.getOrDefault(day, 0L).intValue());
            }
        }

        log.info("list: {}", list);

        InstructorRegistrationChartResponse response = new InstructorRegistrationChartResponse();
        response.setLabels(labels);
        response.setTotalRegistrations(totals);

        return response;
    }
}
