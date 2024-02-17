package com.designofficems.designofficemanagementsystem.dto.employeerate;

import com.designofficems.designofficemanagementsystem.util.CategoryType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEmployeeRateDTO {

    private String name;
    private CategoryType category;
    private Integer employeeId;
    private double rate;
    private String currency;


}
