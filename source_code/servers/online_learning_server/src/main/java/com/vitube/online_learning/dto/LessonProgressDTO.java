package com.vitube.online_learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonProgressDTO {
    String id;
    String userId;
    String lessonId;
    boolean completed;

    long watchedTime;
    LocalDateTime updatedAt;
}
