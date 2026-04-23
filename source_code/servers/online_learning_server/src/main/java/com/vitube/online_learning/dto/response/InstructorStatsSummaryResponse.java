package com.vitube.online_learning.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class InstructorStatsSummaryResponse {
    private long totalCourses;
    private long publishedCourses;
    private long totalStudents;
    private long totalVideos;
    private BigDecimal totalEarnings;
    private Double averageRating;
}