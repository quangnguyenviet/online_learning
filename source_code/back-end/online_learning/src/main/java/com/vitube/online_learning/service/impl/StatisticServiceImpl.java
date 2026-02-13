package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.response.CourseStatsResponse;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Lớp triển khai các phương thức liên quan đến thống kê.
 */
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final UserRepository userRepository;
    private final InstructorStatisticRepository instructorStatisticRepository;
    private final InstructorStatisticMapper instructorStatisticMapper;

    /**
     * Lấy thống kê về khóa học của giảng viên.
     *
     * @param instructorId ID của giảng viên.
     * @return Đối tượng phản hồi thống kê.
     */
    public StatisticResponse getCourseStatistic(String instructorId) {
        StatisticResponse response = new StatisticResponse();
        List<CourseStatsResponse> courseStatsRespons = new ArrayList<>();
        long totalRegistrations = 0;
        BigDecimal totalIncome = BigDecimal.ZERO;
        int totalCourses = 0;

        // Tìm giảng viên theo ID
        User instructor = userRepository.findById(instructorId).orElse(null);
        if (instructor == null) {
            return null;
        }

        // Lấy danh sách khóa học của giảng viên
        Set<Course> courses = instructor.getCourses();
        totalCourses = courses.size();
        if (courses == null || courses.isEmpty()) {
            return null;
        }

        // Tính toán thống kê cho từng khóa học
        for (Course course : courses) {
            CourseStatsResponse result = new CourseStatsResponse();
            result.setTitle(course.getTitle());

            result.setTotalRegistrations(course.getRegisters().size());
            BigDecimal totalEarnings = BigDecimal.ZERO;
            for (Register register : course.getRegisters()) {
                totalEarnings = totalEarnings.add(register.getPrice());
            }
            result.setTotalEarnings(totalEarnings);
            totalRegistrations += result.getTotalRegistrations();
            totalIncome = totalIncome.add(totalEarnings);

            courseStatsRespons.add(result);
        }
        response.setCourseStats(courseStatsRespons);
        response.setTotalCourses(totalCourses);
        response.setTotalRegistrations(totalRegistrations);
        response.setTotalIncome(totalIncome);
        return response;
    }

    /**
     * Lấy thống kê về giảng viên theo tháng.
     *
     * @param instructorId ID của giảng viên.
     * @return Danh sách phản hồi thống kê giảng viên.
     */
    public List<InstructorStatisticResponse> getInstructorStatistic(String instructorId) {
        // Tìm giảng viên theo ID
        User instructor = userRepository.findById(instructorId).orElse(null);
        List<InstructorStatic> instructorStatics = instructor.getInstructorStatics();
        int nowYear = java.time.LocalDate.now().getYear();
        int nowMonth = java.time.LocalDate.now().getMonthValue();
        List<InstructorStatisticResponse> instructorStatisticResponses = new ArrayList<>();

        // Kiểm tra và thêm thống kê cho các tháng chưa có dữ liệu
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

        // Chuyển đổi dữ liệu thống kê thành phản hồi
        for (InstructorStatic instructorStatic : instructorStatics) {
            InstructorStatisticResponse response = instructorStatisticMapper.entityToResponse(instructorStatic);
            instructorStatisticResponses.add(response);
        }

        // Sắp xếp danh sách thống kê theo thứ tự
        Collections.sort(instructorStatisticResponses);
        return instructorStatisticResponses;
    }
}