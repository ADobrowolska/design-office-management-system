package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRateRepository employeeRateRepository;

    @Autowired
    private AssignProjectRepository assignProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    protected void setUp() throws Exception {
        employeeRateRepository.deleteAll();
        assignProjectRepository.deleteAll();
        projectRepository.deleteAll();
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
        userRepository.deleteAll();
    }


}
