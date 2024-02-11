package com.designofficems.designofficemanagementsystem.dto.currency;

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
public class NBPRateDTO {

    private LocalDate effectiveDate;
    private BigDecimal mid;

}
