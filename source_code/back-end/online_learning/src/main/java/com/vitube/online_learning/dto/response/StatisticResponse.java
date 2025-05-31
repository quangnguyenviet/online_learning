package com.vitube.online_learning.dto.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticResponse {
    private float totalIncome = 0;
    private long totalRegistrations = 0;
    private int totalCourses = 0;
    private List<CourseStatisticResponse> courseStats;
}
