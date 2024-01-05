package com.designofficems.designofficemanagementsystem.dto.user;

import com.designofficems.designofficemanagementsystem.util.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

}
