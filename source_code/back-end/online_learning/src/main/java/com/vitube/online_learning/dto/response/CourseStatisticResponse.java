package com.vitube.online_learning.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseStatisticResponse {
    private String title;
    private int totalRegistrations;
    private float totalEarnings;
}
