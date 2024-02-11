package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.Currency;
import com.designofficems.designofficemanagementsystem.util.webclient.CurrencyWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrencyService {

    private final CurrencyWebClient currencyWebClient;

    @Autowired
    public CurrencyService(CurrencyWebClient currencyWebClient) {
        this.currencyWebClient = currencyWebClient;
    }

    public Currency getCurrencyRate(String code) {
        log.info(currencyWebClient.getClientResponse(code).toString());
        return currencyWebClient.getCurrency(code);
    }

}
