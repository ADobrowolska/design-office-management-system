package com.designofficems.designofficemanagementsystem.dto.cost;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeDTO;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class CostResponseDTO {

    private ProjectDTO project;
    private EmployeeDTO employee;
    private LocalDate occurrenceDate;
    private BigDecimal total;
    private List<CostDTO> entries;
}
