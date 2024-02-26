package com.designofficems.designofficemanagementsystem.facade;

import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.service.EmployeeRateService;
import com.designofficems.designofficemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.InstanceAlreadyExistsException;
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
        return employeeRateService.getEmployeeRates(employee);
    }

    public List<EmployeeRate> getEmployeeRate(Integer employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<EmployeeRate> employeeRates = employeeRateService.getEmployeeRates(employee);
        return employeeRates;
    }

    public EmployeeRate addEmployeeRate(EmployeeRate employeeRate) throws InstanceAlreadyExistsException {
        return employeeRateService.addEmployeeRate(employeeRate);
    }

    public EmployeeRate editEmployeeRate(EmployeeRate employeeRate) {
        return employeeRateService.editEmployeeRate(employeeRate);
    }
}
