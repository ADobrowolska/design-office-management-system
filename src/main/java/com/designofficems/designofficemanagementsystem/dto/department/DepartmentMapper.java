package com.designofficems.designofficemanagementsystem.dto.department;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {

    public static DepartmentDTO mapToDepartmentDTO(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    public static Department mapToDepartmentModel(DepartmentDTO departmentDTO) {
        return Department.builder()
                .id(departmentDTO.getId())
                .name(departmentDTO.getName())
                .build();
    }

    public static List<DepartmentDTO> mapToDepartmentDTOs(List<Department> departments) {
        return departments.stream()
                .map(DepartmentMapper::mapToDepartmentDTO)
                .collect(Collectors.toList());
    }

    public static DepartmentEmployeeDTO mapToDepartmentEmployeeDTO(Department department, List<Employee> employees) {
        return DepartmentEmployeeDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .employees(EmployeeMapper.mapToEmployeeDTOs(employees))
                .build();
    }


}
