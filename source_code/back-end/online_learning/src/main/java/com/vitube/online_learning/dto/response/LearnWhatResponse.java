package com.vitube.online_learning.dto.response;

import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearnWhatResponse {
    private String id;
    private String desc;
}
