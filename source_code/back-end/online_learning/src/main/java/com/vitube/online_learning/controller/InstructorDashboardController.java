package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.InstructorStatsSummaryResponse;
import com.vitube.online_learning.service.InstructorDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InstructorDashboardController {
    private final InstructorDashboardService instructorDashboardService;

    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    @GetMapping("/instructor/stats/summary")
    public ApiResponse<InstructorStatsSummaryResponse> getInstructorDashboardSummary() {
        InstructorStatsSummaryResponse response = instructorDashboardService.getInstructorDashboardSummary();
        return ApiResponse.<InstructorStatsSummaryResponse>builder().status(1000).data(response).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_INSTRUCTOR')")
    @GetMapping("/instructor/stats/registrations")
    public ApiResponse<?> getRegistrationChart(@RequestParam String filter) {
        log.info("Inside getRegistrationChart() of InstructorDashboardController");

        var response = instructorDashboardService.getRegistrationChart(filter);

        return ApiResponse.builder()
                .status(1000)
                .data(response)
                .build();
    }



}
