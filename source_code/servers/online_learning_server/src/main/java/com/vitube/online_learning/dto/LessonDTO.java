package com.vitube.online_learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDTO {
    private String id;
    private String title;
    private String description;
    private Long duration;
    private String videoUrl;
    private String presignedUrl;
    private String courseId;
    private LocalDateTime createdAt;
    private Boolean isPreview;
    private String status;
    private LessonProgressDTO lessonProgress;
}
