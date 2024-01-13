package com.designofficems.designofficemanagementsystem.dto.project;

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

    public static ProjectDTO mapToProjectDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .budget(project.getBudget())
                .employeeId(project.getEmployee().getId())
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
