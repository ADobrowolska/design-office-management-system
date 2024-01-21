package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateDTO;
import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateMapper;
import com.designofficems.designofficemanagementsystem.facade.EmployeeRateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeRateController {

    private final EmployeeRateFacade employeeRateFacede;

    @Autowired
    public EmployeeRateController(EmployeeRateFacade employeeRateFacede) {
        this.employeeRateFacede = employeeRateFacede;
    }

    @GetMapping("/employeerates")
    public ResponseEntity<List<EmployeeRateDTO>> getEmployeeRate() {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTOs(employeeRateFacede.getEmployeeRate()));
    }



}
