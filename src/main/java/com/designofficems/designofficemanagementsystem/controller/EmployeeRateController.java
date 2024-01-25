package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employeerate.CreateEmployeeRateDTO;
import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateDTO;
import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateMapper;
import com.designofficems.designofficemanagementsystem.facade.EmployeeRateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeRateController {

    private final EmployeeRateFacade employeeRateFacade;

    @Autowired
    public EmployeeRateController(EmployeeRateFacade employeeRateFacede) {
        this.employeeRateFacade = employeeRateFacede;
    }

    @GetMapping("/employee/rate")
    public ResponseEntity<List<EmployeeRateDTO>> getEmployeeRate() {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTOs(employeeRateFacade.getEmployeeRate()));
    }

    @GetMapping("/employee/{id}/rate")
    public ResponseEntity<List<EmployeeRateDTO>> getEmployeeRate(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTOs(employeeRateFacade.getEmployeeRate(employeeId)));
    }

    @PostMapping("/employee/rate")
    public ResponseEntity<EmployeeRateDTO> addEmployeeRate(@RequestBody CreateEmployeeRateDTO employeeRateDTO) {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTO(
                employeeRateFacade.addEmployeeRate(EmployeeRateMapper.mapToEmployeeRateModel(employeeRateDTO))));
    }

    @PutMapping("employee/rate")
    public ResponseEntity<EmployeeRateDTO> editEmployeeRate(@RequestBody EmployeeRateDTO employeeRateDTO) {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTO(
                employeeRateFacade.editEmployeeRate(EmployeeRateMapper.mapToEmployeeRateModel(employeeRateDTO))));
    }



}
