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
public class InstructorCourseResponse {
    private String id;
    private String title;
    private Double price;
    private Integer discount;
    private String imageUrl;
    private Boolean published;
    private String categoryName;
    private Integer numberOfLessons;
    private String duration;
    private Long totalDurationInSeconds;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
    private int totalRegistrations;
    private BigDecimal totalEarnings;
}
