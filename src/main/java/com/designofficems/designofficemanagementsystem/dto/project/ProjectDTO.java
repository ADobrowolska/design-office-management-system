package com.designofficems.designofficemanagementsystem.dto.project;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProjectDTO {

    private Integer id;
    private String name;
    private BigDecimal budget;
    private Integer employeeId;
}
