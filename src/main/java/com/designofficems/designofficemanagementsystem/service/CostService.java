package com.designofficems.designofficemanagementsystem.service;


import com.designofficems.designofficemanagementsystem.model.Cost;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.CostRepository;
import com.designofficems.designofficemanagementsystem.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CostService {

    private final CostRepository costRepository;
    private final EmployeeRateService employeeRateService;

    @Autowired
    public CostService(CostRepository costRepository, EmployeeRateService employeeRateService) {
        this.costRepository = costRepository;
        this.employeeRateService = employeeRateService;
    }

    @Transactional
    public List<Cost> add(Project project, Employee employee, LocalDate date, Long quantity) {
        List<EmployeeRate> employeeRates = employeeRateService.getEmployeeRates(employee);
        List<Cost> costs = new ArrayList<>();
        for (EmployeeRate employeeRate : employeeRates) {
            Cost cost = createCost(project, date, quantity, employeeRate);
            costs.add(cost);
        }
        return costRepository.saveAll(costs);
    }

    private Cost createCost(Project project, LocalDate date, Long quantity, EmployeeRate employeeRate) {
        Cost cost = new Cost();
        cost.setProject(project);
        cost.setEmployeeRate(employeeRate);
        cost.setQuantity(quantity);
        cost.setOccurrenceDate(DateTimeUtils.toInstant(date));
        return cost;
    }


}
