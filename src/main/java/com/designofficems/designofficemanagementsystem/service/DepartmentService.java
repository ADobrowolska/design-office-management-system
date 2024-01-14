package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeService employeeService) {
        this.departmentRepository = departmentRepository;
        this.employeeService = employeeService;
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public List<Department> getDepartmentsByParam(String searchBy) {
        return departmentRepository.findAllDepartmentsContainingParam(searchBy.toLowerCase());
    }

    public Optional<Department> findById(Integer id) {
        return departmentRepository.findById(id);
    }


}
