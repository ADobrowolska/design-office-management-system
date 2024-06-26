package com.designofficems.designofficemanagementsystem.facade;

import com.designofficems.designofficemanagementsystem.dto.assignproject.AssignProjectDTO;
import com.designofficems.designofficemanagementsystem.dto.assignproject.AssignProjectMapper;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectCostDTO;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectEmployeeDTO;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectMapper;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.service.AssignProjectService;
import com.designofficems.designofficemanagementsystem.service.CostService;
import com.designofficems.designofficemanagementsystem.service.EmployeeService;
import com.designofficems.designofficemanagementsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.InstanceAlreadyExistsException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class ProjectFacade {

    private final ProjectService projectService;
    private final AssignProjectService assignProjectService;
    private final EmployeeService employeeService;
    private final CostService costService;

    @Autowired
    public ProjectFacade(ProjectService projectService, AssignProjectService assignProjectService,
                         EmployeeService employeeService, CostService costService) {
        this.projectService = projectService;
        this.assignProjectService = assignProjectService;
        this.employeeService = employeeService;
        this.costService = costService;
    }

    public ProjectEmployeeDTO getProjectWithEmployees(Integer projectId) {
        Project project = projectService.findById(projectId);
        return ProjectMapper.mapToProjectEmployeeDTO(project, assignProjectService.getEmployeesByProject(project));
    }

    public List<ProjectEmployeeDTO> getProjectsWithEmployees() {
        List<Project> projects = projectService.getProjects();
        List<ProjectEmployeeDTO> projectEmployeeDTOs = new ArrayList<>();
        for (Project proj : projects) {
            projectEmployeeDTOs.add(ProjectMapper.mapToProjectEmployeeDTO(proj, assignProjectService.getEmployeesByProject(proj)));
        }
        return projectEmployeeDTOs;
    }

    public AssignProjectDTO editProjectEmployee(Project project, Integer employeeId) throws InstanceAlreadyExistsException {
        if (projectService.checkIfProjectExists(project)) {
            if (employeeService.checkIfEmployeeExists(employeeId)) {
                if (!assignProjectService.checkIfEmployeeIsAssignedToProject(project.getId(), employeeId)) {
                    Employee employee = employeeService.getEmployee(employeeId);
                    return AssignProjectMapper.mapToAssignProjectDTO(
                            assignProjectService.assignEmployeeToProject(project, employee));
                } else {
                    throw new InstanceAlreadyExistsException("This employee is already assigned to this project");
                }
            } else {
                throw new NoSuchElementException("This employee does not exist");
            }
        } else {
            throw new NoSuchElementException("Project does not exist");
        }
    }

    public ProjectCostDTO getCostPerProject(int projectId, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, BigDecimal> totalByDate = costService.getCostPerProject(projectId, startDate, endDate);
        return ProjectMapper.mapToProjectCostDTO(totalByDate);
    }


}
