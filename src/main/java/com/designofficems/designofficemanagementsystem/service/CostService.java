package com.designofficems.designofficemanagementsystem.service;


import com.designofficems.designofficemanagementsystem.model.Cost;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.CostRepository;
import com.designofficems.designofficemanagementsystem.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CostService {

    private final CostRepository costRepository;
    private final EmployeeRateService employeeRateService;
    private static final Logger log = LoggerFactory.getLogger(CostService.class);

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
        cost.setCreationDate(Instant.now());
        return cost;
    }

    public List<Cost> getCosts(Employee employee) {
        return costRepository.findAllByEmployeeRateEmployee(employee,
                Sort
                        .by(Sort.Direction.DESC, "occurrenceDate")
                        .and(Sort.by(Sort.Direction.DESC, "creationDate")));
    }

    public List<Cost> getCostsByDay(Employee employee, LocalDate date) {
        return costRepository.findAllByEmployeeRateEmployeeAndOccurrenceDate(employee,
                DateTimeUtils.toInstant(date));
    }

    @Transactional
    public void deleteCost(Integer id, Employee employee) {
        costRepository.deleteByIdAndEmployeeRateEmployee(id, employee);
        log.info("Cost removed");
    }

    public Map<LocalDate, BigDecimal> getCostPerProject(int projectId, LocalDate startDate, LocalDate endDate) {
        List<Cost> costs =
                costRepository.findAllByProjectIdAndOccurrenceDateGreaterThanEqualAndOccurrenceDateLessThanEqual(
                        projectId, DateTimeUtils.toInstant(startDate), DateTimeUtils.toInstant(endDate));
        Map<LocalDate, BigDecimal> map = new HashMap<>();
        for (Cost cost : costs) {
            LocalDate day = DateTimeUtils.toLocalDate(cost.getOccurrenceDate());
            if (map.containsKey(day)) {
                BigDecimal current = map.get(day);
                BigDecimal costValue = getSubtotal(cost);
                BigDecimal aggregate = current.add(costValue);
                map.put(day, aggregate);
            } else {
                map.put(day, getSubtotal(cost));
            }
        }
        return map;
    }

    public static BigDecimal getTotal(List<Cost> costs) {
        BigDecimal total = BigDecimal.ZERO;
        for (Cost cost : costs) {
            total = total.add(getSubtotal(cost));
        }
        return total;
    }

    public static BigDecimal getSubtotal(Cost cost) {
        BigDecimal rateByMinute = BigDecimal.valueOf(cost.getEmployeeRate().getRate())
                .divide(BigDecimal.valueOf(60), RoundingMode.HALF_EVEN);
        BigDecimal subtotal = rateByMinute.multiply(BigDecimal.valueOf(cost.getQuantity()));
        return subtotal;
    }
}
