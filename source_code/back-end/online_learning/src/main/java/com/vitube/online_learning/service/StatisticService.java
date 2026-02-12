package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.InstructorStatisticResponse;
import com.vitube.online_learning.dto.response.StatisticResponse;

import java.util.List;

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