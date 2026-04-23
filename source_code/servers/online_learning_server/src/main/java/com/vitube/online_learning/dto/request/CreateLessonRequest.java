package com.vitube.online_learning.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateLessonRequest {
    private String id;
    private String title;
    private String description;
    private String courseId;
}
