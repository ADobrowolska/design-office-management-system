package com.designofficems.designofficemanagementsystem.facade;

import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.service.EmployeeRateService;
import com.designofficems.designofficemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeRateFacade {

    private final EmployeeService employeeService;
    private final EmployeeRateService employeeRateService;

    @Autowired
    public EmployeeRateFacade(EmployeeService employeeService, EmployeeRateService employeeRateService) {
        this.employeeService = employeeService;
        this.employeeRateService = employeeRateService;
    }

    public List<EmployeeRate> getEmployeeRate() {
        Employee employee = employeeService.getEmployee();
        return employeeRateService.getEmployeeRate(employee);
    }

    public List<EmployeeRate> getEmployeeRate(int employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return employeeRateService.getEmployeeRate(employee);
    }

    public EmployeeRate addEmployeeRate(EmployeeRate employeeRate) {
        return employeeRateService.addEmployeeRate(employeeRate);
    }
}
