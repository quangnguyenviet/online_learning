package com.vitube.online_learning.dto.request;

import com.vitube.online_learning.enums.GenderEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
    private GenderEnum gender;
}
