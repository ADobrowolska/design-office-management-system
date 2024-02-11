package com.designofficems.designofficemanagementsystem.util.webclient;

import com.designofficems.designofficemanagementsystem.dto.currency.CurrencyMapper;
import com.designofficems.designofficemanagementsystem.dto.currency.OpenCurrencyDTO;
import com.designofficems.designofficemanagementsystem.model.Currency;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CurrencyWebClient {

    private WebClient.Builder builder = WebClient.builder().baseUrl("http://api.nbp.pl");

    public OpenCurrencyDTO getClientResponse(String code) {

        return builder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/exchangerates/rates/a/{code}")
                        .build(code))
                .retrieve()
                .bodyToMono(OpenCurrencyDTO.class)
                .block();
    }


    public Currency getCurrency(String code) {
        return CurrencyMapper.mapToCurrencyModel(getClientResponse(code));
    }


}
