package com.vitube.online_learning.dto.response;

import com.vitube.online_learning.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
}
