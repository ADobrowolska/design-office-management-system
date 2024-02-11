package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.currency.CurrencyDTO;
import com.designofficems.designofficemanagementsystem.dto.currency.CurrencyMapper;
import com.designofficems.designofficemanagementsystem.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    @GetMapping("/currencies/{code}")
    public ResponseEntity<CurrencyDTO> getCurrencyRate(@PathVariable String code) {
        return ResponseEntity.ok(CurrencyMapper.mapToCurrencyDTO(currencyService.getCurrencyRate(code)));
    }


}
