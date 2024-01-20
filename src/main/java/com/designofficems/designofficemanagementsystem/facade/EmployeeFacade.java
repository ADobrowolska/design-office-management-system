package com.designofficems.designofficemanagementsystem.facade;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeProjectDTO;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.service.AssignProjectService;
import com.designofficems.designofficemanagementsystem.service.EmployeeService;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFacade {

    private final EmployeeService employeeService;
    private final AssignProjectService assignProjectService;

    public EmployeeFacade(EmployeeService employeeService, AssignProjectService assignProjectService) {
        this.employeeService = employeeService;
        this.assignProjectService = assignProjectService;
    }


    public EmployeeProjectDTO getEmployeeWithProjects() {
        Employee employee = employeeService.getEmployee();
        return EmployeeMapper.mapToEmployeeProjectDTO(employee, assignProjectService.getProjectsByEmployee(employee));
    }
}
