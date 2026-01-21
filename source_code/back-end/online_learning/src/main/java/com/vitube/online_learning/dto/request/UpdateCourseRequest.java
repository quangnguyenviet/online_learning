package com.vitube.online_learning.dto.request;

import com.vitube.online_learning.enums.LevelEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCourseRequest {
    private String id;
    private String title;
    private Double price;
    private Integer discount;
    private String shortDesc;
    private Boolean published;
    private LevelEnum level;
    private Integer categoryId; // changed from int to Integer to allow null (no-change)
    private List<String> deleteObjectiveIds;
    private List<String> newObjectives;
}
