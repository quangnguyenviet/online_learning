package com.vitube.online_learning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vitube.online_learning.dto.response.InstructorStatisticResponse;
import org.springframework.stereotype.Service;

import com.vitube.online_learning.dto.response.CourseStatisticResponse;
import com.vitube.online_learning.dto.response.StatisticResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.InstructorStatic;
import com.vitube.online_learning.entity.Register;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.InstructorStatisticRepository;
import com.vitube.online_learning.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Interface cung cấp các phương thức liên quan đến thống kê.
 */
public interface StatisticService {

    /**
     * Lấy thống kê của các khóa học theo ID của giảng viên.
     *
     * @param instructorId ID của giảng viên.
     * @return Đối tượng phản hồi thống kê.
     */
    public StatisticResponse getCourseStatistic(String instructorId);

    /**
     * Lấy thống kê của giảng viên theo ID của giảng viên.
     *
     * @param instructorId ID của giảng viên.
     * @return Danh sách phản hồi thống kê của giảng viên.
     */
    public List<InstructorStatisticResponse> getInstructorStatistic(String instructorId);
}