package com.designofficems.designofficemanagementsystem.dto.cost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyCostDTO {

    private BigDecimal total;
    private LocalDate date;

}
