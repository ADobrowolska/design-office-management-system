package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeDTO;
import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeProjectDTO;
import com.designofficems.designofficemanagementsystem.facade.EmployeeFacade;
import com.designofficems.designofficemanagementsystem.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private final EmployeeFacade employeeFacade;

    @Autowired
    public EmployeeController(EmployeeFacade employeeFacade) {
        this.employeeFacade = employeeFacade;
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.mapToEmployeeModel(employeeDTO);
        EmployeeDTO receivedEmployeeDTO = EmployeeMapper.mapToEmployeeDTO(employeeFacade.addEmployee(employee));
        return ResponseEntity.ok(receivedEmployeeDTO);
    }

    @GetMapping("/employees")
    public ResponseEntity<EmployeeDTO> getEmployee() {
        EmployeeDTO receivedEmployeeDTO = EmployeeMapper.mapToEmployeeDTO(employeeFacade.getEmployee());
        return ResponseEntity.ok(receivedEmployeeDTO);
    }

    @GetMapping("/employees/project")
    public ResponseEntity<EmployeeProjectDTO> getEmployeeWithProjects() {
        return ResponseEntity.ok(employeeFacade.getEmployeeWithProjects());
    }

    @PutMapping("/employee")
    public ResponseEntity<EmployeeDTO> editEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.mapToEmployeeModel(employeeDTO);
        EmployeeDTO receivedEmployeeDTO = EmployeeMapper.mapToEmployeeDTO(employeeFacade.editEmployee(employee));
        return ResponseEntity.ok(receivedEmployeeDTO);
    }


}
