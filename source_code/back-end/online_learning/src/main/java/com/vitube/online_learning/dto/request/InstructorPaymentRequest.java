package com.vitube.online_learning.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorPaymentRequest extends BaseRequest{
    private Integer month;
    private Integer year;
    private String id;
    // ngày trả
    private LocalDateTime paidAt;
}
