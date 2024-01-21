package com.designofficems.designofficemanagementsystem.facade;

import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRateRepository;
import com.designofficems.designofficemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeRateFacade {

    private final EmployeeService employeeService;
    private final EmployeeRateRepository employeeRateRepository;

    @Autowired
    public EmployeeRateFacade(EmployeeService employeeService, EmployeeRateRepository employeeRateRepository) {
        this.employeeService = employeeService;
        this.employeeRateRepository = employeeRateRepository;
    }

    public List<EmployeeRate> getEmployeeRate() {
        Employee employee = employeeService.getEmployee();
        return employeeRateRepository.findAllByEmployee(employee);
    }

    public List<EmployeeRate> getEmployeeRate(int employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return employeeRateRepository.findAllByEmployee(employee);
    }



}
