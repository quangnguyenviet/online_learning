package com.vitube.online_learning.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRequest {
    private String courseId;
    private String title;
    private MultipartFile file;
    private String description;
}
