package com.designofficems.designofficemanagementsystem.dto.employee;

import com.designofficems.designofficemanagementsystem.dto.project.ProjectDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmployeeProjectDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer departmentId;
    private List<ProjectDTO> projects;

}
