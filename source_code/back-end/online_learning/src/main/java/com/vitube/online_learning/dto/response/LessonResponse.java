package com.vitube.online_learning.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponse {
    private String id;
    private String title;
    private String lessonKey;
    private String videoUrl;
}
