package com.vitube.online_learning.dto.request;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveRequireRequest {
    private List<String> delIdList;
    private List<Map<String, Object>> otherList;
}
