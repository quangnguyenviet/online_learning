package com.vitube.online_learning.dto.request;

import java.util.List;
import java.util.Map;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveRequireRequest {
    private List<String> delIdList;
    private List<Map<String, Object>> otherList;
}
