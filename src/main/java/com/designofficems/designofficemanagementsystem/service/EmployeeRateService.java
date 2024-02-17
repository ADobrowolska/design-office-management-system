package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.model.NBPRate;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRateRepository;
import com.designofficems.designofficemanagementsystem.util.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class EmployeeRateService {

    private static final String DEFAULT_CURRENCY = "PLN";
    private final EmployeeRateRepository employeeRateRepository;
    private final CurrencyService currencyService;

    @Autowired
    public EmployeeRateService(EmployeeRateRepository employeeRateRepository, CurrencyService currencyService) {
        this.employeeRateRepository = employeeRateRepository;
        this.currencyService = currencyService;
    }

    public EmployeeRate addEmployeeRate(EmployeeRate employeeRate) {
        if (DEFAULT_CURRENCY.equals(employeeRate.getCurrency().toUpperCase())) {
            employeeRate.setRate(employeeRate.getRate());
        } else {
            employeeRate.setRate(convertRate(employeeRate.getRate(), employeeRate.getCurrency()));
            employeeRate.setCurrency(DEFAULT_CURRENCY);
        }
        return employeeRateRepository.save(employeeRate);
    }

    public List<EmployeeRate> getEmployeeRates(Employee employee) {
        return employeeRateRepository.findAllByEmployee(employee);
    }

    public EmployeeRate getEmployeeRate(Employee employee, CategoryType categoryType) {
        return employeeRateRepository.findByEmployeeAndCategory(employee, categoryType);
    }

    @Transactional
    public EmployeeRate editEmployeeRate(EmployeeRate employeeRate) {
        EmployeeRate existed = getEmployeeRate(employeeRate.getEmployee(), employeeRate.getCategory());
        existed.setName(employeeRate.getName());
        existed.setRate(employeeRate.getRate());
        return employeeRateRepository.save(existed);
    }

    private double convertRate(double rate, String currency) {
        BigDecimal mid = currencyService.getCurrencyRate(currency).getRates().stream()
                .max(Comparator.comparing(NBPRate::getEffectiveDate))
                .orElseThrow().getMid();
        return BigDecimal.valueOf(rate).multiply(mid).doubleValue();
    }

}
