package com.designofficems.designofficemanagementsystem.dto.department;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DepartmentEmployeeDTO {

    private Integer id;
    private String name;
    private List<EmployeeDTO> employees;

}
