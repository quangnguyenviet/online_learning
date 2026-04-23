package com.vitube.online_learning.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorRegistrationChartResponse {
    private List<Integer> totalRegistrations;
    private List<String> labels;
}
