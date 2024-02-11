package com.designofficems.designofficemanagementsystem.dto.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenCurrencyDTO {

    private String table;
    private String currency;
    private String code;
    private List<NBPRateDTO> rates;

}
