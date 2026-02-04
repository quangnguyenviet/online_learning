package com.vitube.online_learning.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponse implements Comparable<LessonResponse> {
    private String id;
    private String title;
    private String lessonKey;
    private String videoUrl;
    private String description;
    private long duration;
    private LocalDateTime createdAt;


    @Override
    public int compareTo(LessonResponse o) {
        return this.createdAt.compareTo(o.createdAt);
    }
}
