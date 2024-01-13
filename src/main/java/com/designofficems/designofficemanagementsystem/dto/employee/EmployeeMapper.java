package com.designofficems.designofficemanagementsystem.dto.employee;

import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDTO mapToEmployeeDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .departmentId(employee.getDepartment().getId())
                .build();
    }

    public static Employee mapToEmployeeModel(EmployeeDTO employeeDTO) {
        return Employee.builder()
                .id(employeeDTO.getId())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .department(Department.ofId(employeeDTO.getDepartmentId()))
                .build();
    }

    public static List<EmployeeDTO> mapToEmployeeDTOs(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDTO)
                .collect(Collectors.toList());
    }

    public static List<Employee> mapToEmployees(List<EmployeeDTO> employeeDTOs) {
        return employeeDTOs.stream()
                .map(EmployeeMapper::mapToEmployeeModel)
                .collect(Collectors.toList());
    }
}
