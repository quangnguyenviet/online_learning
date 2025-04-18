package com.vitube.online_learning.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequireResponse {
    private String id;
    private String desc;
    private String courseId;
}
