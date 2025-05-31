package com.vitube.online_learning.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {
    private String id;
    private String title;
    private String instructorId;
    private float price;
    private double discount;
    private List<LessonResponse> lessons;
    private List<LearnWhatResponse> learnWhats;
    private List<RequireResponse> requires;
    private String short_desc;
    private int number_of_lessons;
    private int hour;
    private int minute;
    private int second;
    private String imageUrl;
    private Boolean published;
}
