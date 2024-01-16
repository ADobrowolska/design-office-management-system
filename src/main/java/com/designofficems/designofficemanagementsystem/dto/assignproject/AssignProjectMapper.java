package com.designofficems.designofficemanagementsystem.dto.assignproject;


import com.designofficems.designofficemanagementsystem.model.AssignProject;

public class AssignProjectMapper {

    public static AssignProjectDTO mapToAssignProjectDTO(AssignProject assignProject) {
        return AssignProjectDTO.builder()
                .id(assignProject.getId())
                .projectId(assignProject.getId())
                .employeeId(assignProject.getId())
                .build();
    }

}
