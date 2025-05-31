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

public interface StatisticService {


    public StatisticResponse getCourseStatistic(String instructorId);

    public List<InstructorStatisticResponse> getInstructorStatistic(String instructorId);
}
