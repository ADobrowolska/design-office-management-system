package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeDTO;
import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.mapToEmployeeModel(employeeDTO);
        EmployeeDTO receivedEmployeeDTO = EmployeeMapper.mapToEmployeeDTO(employeeService.addEmployee(employee));
        return ResponseEntity.ok(receivedEmployeeDTO);
    }

    @GetMapping("/employees")
    public ResponseEntity<EmployeeDTO> getEmployee() {
        EmployeeDTO receivedEmployeeDTO = EmployeeMapper.mapToEmployeeDTO(employeeService.getEmployee());
        return ResponseEntity.ok(receivedEmployeeDTO);
    }

    @PutMapping("/employee")
    public ResponseEntity<EmployeeDTO> editEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.mapToEmployeeModel(employeeDTO);
        EmployeeDTO receivedEmployeeDTO = EmployeeMapper.mapToEmployeeDTO(employeeService.editEmployee(employee));
        return ResponseEntity.ok(receivedEmployeeDTO);
    }


}
