package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
