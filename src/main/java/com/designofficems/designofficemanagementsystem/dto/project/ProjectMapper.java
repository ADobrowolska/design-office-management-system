package com.designofficems.designofficemanagementsystem.dto.project;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;

import java.util.List;
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


}
