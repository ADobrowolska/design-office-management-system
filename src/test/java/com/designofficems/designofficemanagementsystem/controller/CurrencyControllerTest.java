package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.currency.CurrencyDTO;
import com.designofficems.designofficemanagementsystem.dto.currency.NBPRateDTO;
import com.designofficems.designofficemanagementsystem.dto.currency.OpenCurrencyDTO;
import com.designofficems.designofficemanagementsystem.service.CurrencyService;
import com.designofficems.designofficemanagementsystem.util.webclient.CurrencyWebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class CurrencyControllerTest extends BaseTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurrencyService currencyService;

    @SpyBean
    private CurrencyWebClient currencyWebClient;

    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }


    @Test
    void shouldGetCurrencyRate() throws Exception {
        String code = "EUR";
        OpenCurrencyDTO openCurrencyDTO = new OpenCurrencyDTO();
        List<NBPRateDTO> rates = new ArrayList<>();
        rates.add(new NBPRateDTO(LocalDate.now(), BigDecimal.valueOf(4.5)));
        openCurrencyDTO.setRates(rates);
        doReturn(openCurrencyDTO).when(currencyWebClient).getClientResponse(anyString());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/currencies/" + code)
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        CurrencyDTO receivedCurrencyRate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CurrencyDTO.class);
        Assertions.assertThat(receivedCurrencyRate.getCurrencyRate()).isEqualTo(BigDecimal.valueOf(4.5));
    }





}