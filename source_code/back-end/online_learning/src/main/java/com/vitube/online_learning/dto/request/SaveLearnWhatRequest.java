package com.vitube.online_learning.dto.request;

import java.util.List;
import java.util.Map;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveLearnWhatRequest {
    // danh sach id can xoa
    private List<String> delIdList;
    // danh sach cac thuc the can them moi hay cap nhat
    private List<Map<String, Object>> otherList;
}
