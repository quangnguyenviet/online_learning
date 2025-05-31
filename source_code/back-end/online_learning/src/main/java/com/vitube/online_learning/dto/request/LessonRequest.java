package com.vitube.online_learning.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LessonRequest extends BaseRequest {
    private String courseId;
    private String title;
    private MultipartFile file;
    private String description;
    private Integer order;
    private String tag;
}
