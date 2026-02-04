package com.vitube.online_learning.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.vitube.online_learning.enums.GenderEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String id;
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private GenderEnum gender;
    private String phoneNumber;
    private LocalDateTime createdAt;
//    private String bankName;
//    private String accountNumber;
//    private String accountName;
    private List<RoleDTO> roles;

    public String getFullName() {
        String fName = firstName != null ? firstName : "";
        String lName = lastName != null ? lastName : "";
        return (fName + " " + lName).trim();
    }

}
