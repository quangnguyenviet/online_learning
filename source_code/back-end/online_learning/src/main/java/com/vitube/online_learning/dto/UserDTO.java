package com.vitube.online_learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date dob;
    private String bankName;
    private String accountNumber;
    private String accountName;
    private List<RoleDTO> roles;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
