package com.designofficems.designofficemanagementsystem.facade;

import com.designofficems.designofficemanagementsystem.model.Cost;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.service.CostService;
import com.designofficems.designofficemanagementsystem.service.EmployeeService;
import com.designofficems.designofficemanagementsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CostFacade {

    private final CostService costService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public CostFacade(CostService costService, EmployeeService employeeService, ProjectService projectService) {
        this.costService = costService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }


    public List<Cost> add(Integer projectId, Long quantity, LocalDate date) {
        Employee employee = employeeService.getEmployee();
        Project project = projectService.findById(projectId);
        return costService.add(project, employee, date, quantity);
    }

    public List<Cost> getCosts() {
        return costService.getCosts(employeeService.getEmployee());
    }
}
