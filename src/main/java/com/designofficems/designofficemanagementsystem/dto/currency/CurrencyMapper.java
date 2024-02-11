package com.designofficems.designofficemanagementsystem.dto.currency;

import com.designofficems.designofficemanagementsystem.model.Currency;
import com.designofficems.designofficemanagementsystem.model.NBPRate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyMapper {

    public static CurrencyDTO mapToCurrencyDTO(Currency currency) {
        return CurrencyDTO.builder()
                .date(currency.getDate())
                .currency(currency.getCurrency())
                .code(currency.getCode())
                .currencyRate(currency.getRates().stream()
                        .max(Comparator.comparing(NBPRate::getEffectiveDate))
                        .orElseThrow().getMid())
                .build();
    }

    public static Currency mapToCurrencyModel(OpenCurrencyDTO openCurrencyDTO) {
        return Currency.builder()
                .date(openCurrencyDTO.getRates().stream()
                        .findFirst().get().getEffectiveDate())
                .table(openCurrencyDTO.getTable())
                .currency(openCurrencyDTO.getCurrency())
                .code(openCurrencyDTO.getCode())
                .rates(mapToNBPRatesModel(openCurrencyDTO.getRates()))
                .build();
    }

    public static NBPRate mapToNBPRateModel(NBPRateDTO nbpRatesDTO) {
        return NBPRate.builder()
                .effectiveDate(nbpRatesDTO.getEffectiveDate())
                .mid(nbpRatesDTO.getMid())
                .build();
    }

    public static List<NBPRate> mapToNBPRatesModel(List<NBPRateDTO> nbpRateDTOs) {
        return nbpRateDTOs.stream()
                .map(CurrencyMapper::mapToNBPRateModel)
                .collect(Collectors.toList());
    }


}
