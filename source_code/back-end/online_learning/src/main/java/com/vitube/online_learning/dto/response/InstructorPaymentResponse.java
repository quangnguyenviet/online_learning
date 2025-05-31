package com.vitube.online_learning.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorPaymentResponse {
    private String id;
    private LocalDateTime paidAt;
    private InstructorStatisticResponse statistic;
    private UserResponse instructor;

}
