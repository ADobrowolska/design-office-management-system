package com.designofficems.designofficemanagementsystem.dto.employeerate;

import com.designofficems.designofficemanagementsystem.util.CategoryType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeRateDTO {

    private Integer id;
    private String name;
    private CategoryType category;
    private double rate;
    private Integer employeeId;

}
