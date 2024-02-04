package com.designofficems.designofficemanagementsystem.dto.cost;

import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class CostDTO {

    private Integer id;
    private LocalDate occurrenceDate;
    private Long quantity;
    private BigDecimal subtotal;
    private EmployeeRateDTO employeeRate;

}
