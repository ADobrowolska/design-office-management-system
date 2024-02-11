package com.designofficems.designofficemanagementsystem.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class NBPRate {
    private LocalDate effectiveDate;
    private BigDecimal mid;
}
