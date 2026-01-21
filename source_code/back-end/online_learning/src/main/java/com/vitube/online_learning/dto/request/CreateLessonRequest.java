package com.vitube.online_learning.dto.request;

import com.vitube.online_learning.entity.Course;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
