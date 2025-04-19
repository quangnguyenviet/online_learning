package com.vitube.online_learning.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveRequireRequest {
    private List<String> delIdList;
    private List<Object> addList;
    private List<Object> editList;
}
