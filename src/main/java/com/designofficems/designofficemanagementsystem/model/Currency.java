package com.designofficems.designofficemanagementsystem.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Currency {

    private String table;
    private String currency;
    private String code;
    private List<NBPRate> rates;
    private LocalDate date;

}
