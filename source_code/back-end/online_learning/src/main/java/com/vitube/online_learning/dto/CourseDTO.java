package com.vitube.online_learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.enums.LevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {
    private String id;
    private String title;
    private Double price;
    private Integer discount;
    private List<ObjectiveDTO> objectives;
    private List<RequireResponse> requires;
    private String shortDesc;
    private Integer numberOfLessons;
//    private Integer hour;
//    private Integer minute;
//    private Integer second;
    private String duration;
    private Long totalDurationInSeconds;
    private String imageUrl;
    private Boolean published;
    private MultipartFile imageFile;
    private LevelEnum level;
    private CategoryDTO category;
    private int categoryId;
    private List<LessonDTO> lessons;
}
