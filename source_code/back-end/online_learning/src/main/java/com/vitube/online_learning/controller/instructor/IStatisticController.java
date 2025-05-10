package com.vitube.online_learning.controller.instructor;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.CourseStatisticResponse;
import com.vitube.online_learning.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic-instructor")
@RequiredArgsConstructor
public class IStatisticController {

    private final StatisticService statisticService;

    @GetMapping("/course")
    public ApiResponse<List<CourseStatisticResponse>> getCourseStatistic(@RequestParam String inStructorID) {
        List<CourseStatisticResponse> responses =  null;

        return ApiResponse.<List<CourseStatisticResponse>>builder()
                .status(1000)
                .data(responses)
                .build();
    }
}
