package com.vitube.online_learning.dto.response;

import lombok.*;

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
    private long durationInSeconds;
    private Integer idx;

    @Override
    public int compareTo(LessonResponse o) {
        return Integer.compare(this.getIdx(), o.getIdx());
    }
}
