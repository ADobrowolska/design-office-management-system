package com.designofficems.designofficemanagementsystem.dto.assignproject;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignProjectDTO {

    private Integer id;
    private Integer employeeId;
    private Integer projectId;

}
