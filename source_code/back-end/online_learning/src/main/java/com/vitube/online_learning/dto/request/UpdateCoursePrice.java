package com.vitube.online_learning.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCoursePrice {
    private String courseId;
    private Float price;
}
