package com.designofficems.designofficemanagementsystem.dto.department;

import com.designofficems.designofficemanagementsystem.model.Department;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {

    public static DepartmentDTO mapToDepartmentDTO(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    public static List<DepartmentDTO> mapToDepartmentDTOs(List<Department> departments) {
        return departments.stream()
                .map(DepartmentMapper::mapToDepartmentDTO)
                .collect(Collectors.toList());
    }

}
