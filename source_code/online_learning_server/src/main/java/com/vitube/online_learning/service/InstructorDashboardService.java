package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.CourseStatsResponse;
import com.vitube.online_learning.dto.response.InstructorRegistrationChartResponse;
import com.vitube.online_learning.dto.response.InstructorStatsSummaryResponse;
import org.springframework.data.domain.Page;

public interface InstructorDashboardService {
    InstructorStatsSummaryResponse getInstructorDashboardSummary();

    InstructorRegistrationChartResponse getRegistrationChart(String filter);

    Page<CourseStatsResponse> getInstructorCoursesStats(int page, int size);
}
