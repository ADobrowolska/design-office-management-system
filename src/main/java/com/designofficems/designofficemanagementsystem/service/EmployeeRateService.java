package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRateRepository;
import com.designofficems.designofficemanagementsystem.util.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeRateService {

    private final EmployeeRateRepository employeeRateRepository;

    @Autowired
    public EmployeeRateService(EmployeeRateRepository employeeRateRepository) {
        this.employeeRateRepository = employeeRateRepository;
    }

    public EmployeeRate addEmployeeRate(EmployeeRate employeeRate) {
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
}
