package com.designofficems.designofficemanagementsystem.dto.employee;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer departmentId;

}
