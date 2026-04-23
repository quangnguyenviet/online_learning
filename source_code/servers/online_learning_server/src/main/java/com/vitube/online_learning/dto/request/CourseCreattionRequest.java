package com.vitube.online_learning.dto.request;

import com.vitube.online_learning.enums.LevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreattionRequest {
    private String title;
    private float price;
    private double discount;
    private List<String> objectives;
    private String shortDesc;
    private Boolean published;
    private LevelEnum level;
    private  Integer categoryId;
}
