package com.designofficems.designofficemanagementsystem.facade;

import com.designofficems.designofficemanagementsystem.dto.department.DepartmentEmployeeDTO;
import com.designofficems.designofficemanagementsystem.dto.department.DepartmentMapper;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.service.DepartmentService;
import com.designofficems.designofficemanagementsystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentFacade {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public DepartmentEmployeeDTO getDepartmentEmployee(Integer departmentId) {
        Department department = departmentService.findById(departmentId).orElseThrow();
        DepartmentEmployeeDTO departmentEmployeeDTO = DepartmentMapper.mapToDepartmentEmployeeDTO(department, employeeService.getEmployeeByDepartment(department));
        return departmentEmployeeDTO;
    }

    public List<DepartmentEmployeeDTO> getDepartmentsWithEmployees() {
        List<Department> departments = departmentService.getDepartments();
        List<DepartmentEmployeeDTO> departmentEmployeeDTOs = new ArrayList<>();
        for (Department dept : departments) {
            departmentEmployeeDTOs.add(DepartmentMapper.mapToDepartmentEmployeeDTO(dept, employeeService.getEmployeeByDepartment(dept)));
        }
        return departmentEmployeeDTOs;
    }



}
