package com.vitube.online_learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vitube.online_learning.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {
    private RoleEnum name;
    private String description;
}
