package com.designofficems.designofficemanagementsystem.model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
public class DailyCost {

    private BigDecimal total;
    private LocalDate date;

}
