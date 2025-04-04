package com.vitube.online_learning.dto.response;

import com.vitube.online_learning.entity.User;
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
    private double price;
}
