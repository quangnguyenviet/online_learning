package com.vitube.online_learning.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequest extends BaseRequest {
    private String title;
    private String instructorId;
    private float price;
    private double discount;
    private String shortDesc;
    private Boolean published = false; // mặc định là false
}
