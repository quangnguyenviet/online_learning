package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.InstructorRegistrationChartResponse;
import com.vitube.online_learning.dto.response.InstructorStatsSummaryResponse;

public interface InstructorDashboardService {
    InstructorStatsSummaryResponse getInstructorDashboardSummary();

    InstructorRegistrationChartResponse getRegistrationChart(String filter);
}
