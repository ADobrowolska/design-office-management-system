package com.designofficems.designofficemanagementsystem.dto.currency;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CurrencyDTO {

    private LocalDate date;
    private String currency;
    private String code;
    private BigDecimal currencyRate;

}
