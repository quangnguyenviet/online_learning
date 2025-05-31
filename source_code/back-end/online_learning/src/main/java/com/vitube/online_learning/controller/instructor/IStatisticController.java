package com.vitube.online_learning.controller.instructor;

import java.util.List;

import com.vitube.online_learning.dto.response.InstructorStatisticResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.StatisticResponse;
import com.vitube.online_learning.entity.InstructorStatic;
import com.vitube.online_learning.service.StatisticService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/statistic-instructor")
@RequiredArgsConstructor
public class IStatisticController {

    private final StatisticService statisticService;

    @GetMapping("/course")
    public ApiResponse<StatisticResponse> getStatisticForCourse(@RequestParam String instructorId) {
        StatisticResponse response = statisticService.getCourseStatistic(instructorId);
        return ApiResponse.<StatisticResponse>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<?> getInstructorStatistic(@RequestParam String instructorId) {
        List<InstructorStatisticResponse> response = statisticService.getInstructorStatistic(instructorId);
        return ApiResponse.<List<InstructorStatisticResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }
}
