package com.designofficems.designofficemanagementsystem.dto.project;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class ProjectEmployeeDTO {

    private Integer id;
    private String name;
    private BigDecimal budget;
    private String description;
    private List<EmployeeDTO> employees;

}
