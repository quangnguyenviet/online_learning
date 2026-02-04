package com.vitube.online_learning.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseStatisticResponse {
    private String title;
    private int totalRegistrations;
    private BigDecimal totalEarnings;
}
