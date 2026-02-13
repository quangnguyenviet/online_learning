package com.vitube.online_learning.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseStatsResponse {
    private String id;
    private String title;
    private int totalRegistrations;
    private BigDecimal totalEarnings;
    private long totalDurationInSeconds;
    private Boolean published;

}
