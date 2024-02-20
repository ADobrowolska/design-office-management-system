package com.designofficems.designofficemanagementsystem.dto.project;

import com.designofficems.designofficemanagementsystem.dto.cost.DailyCostDTO;
import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static Project mapToProjectModel(CreateProjectDTO projectDTO) {
        return Project.builder()
                .name(projectDTO.getName())
                .budget(projectDTO.getBudget())
                .build();
    }

    public static Project mapToProjectModel(ProjectDTO projectDTO) {
        return Project.builder()
                .id(projectDTO.getId())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .budget(projectDTO.getBudget())
                .build();
    }

    public static Project mapToProjectModel(ProjectEmployeeDTO projectEmployeeDTO) {
        return Project.builder()
                .id(projectEmployeeDTO.getId())
                .name(projectEmployeeDTO.getName())
                .description(projectEmployeeDTO.getDescription())
                .budget(projectEmployeeDTO.getBudget())
                .build();
    }

    public static ProjectDTO mapToProjectDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .budget(project.getBudget())
                .description(project.getDescription())
                .build();
    }

    public static ProjectEmployeeDTO mapToProjectEmployeeDTO(Project project, List<Employee> employees) {
        return ProjectEmployeeDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .budget(project.getBudget())
                .description(project.getDescription())
                .employees(EmployeeMapper.mapToEmployeeDTOs(employees))
                .build();
    }

    public static List<Project> mapToProjects(List<CreateProjectDTO> projectDTOs) {
        return projectDTOs.stream()
                .map(ProjectMapper::mapToProjectModel)
                .collect(Collectors.toList());
    }

    public static List<ProjectDTO> mapToProjectDTOs(List<Project> projects) {
        return projects.stream()
                .map(ProjectMapper::mapToProjectDTO)
                .collect(Collectors.toList());
    }

    public static ProjectCostDTO mapToProjectCostDTO(Map<LocalDate, BigDecimal> totalByDate) {
        BigDecimal total = totalByDate.values().stream()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        List<DailyCostDTO> dailyCosts = totalByDate.entrySet().stream()
                .map(entry -> new DailyCostDTO(entry.getValue(), entry.getKey()))
                .toList();

        return new ProjectCostDTO(total, dailyCosts);
    }


}
