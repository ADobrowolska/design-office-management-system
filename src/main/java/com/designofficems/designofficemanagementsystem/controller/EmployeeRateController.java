package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employeerate.CreateEmployeeRateDTO;
import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateDTO;
import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateMapper;
import com.designofficems.designofficemanagementsystem.facade.EmployeeRateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
public class EmployeeRateController {

    private final EmployeeRateFacade employeeRateFacade;

    @Autowired
    public EmployeeRateController(EmployeeRateFacade employeeRateFacade) {
        this.employeeRateFacade = employeeRateFacade;
    }

    @GetMapping("/employee/rate")
    public ResponseEntity<List<EmployeeRateDTO>> getEmployeeRate() {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTOs(employeeRateFacade.getEmployeeRate()));
    }

    @GetMapping("/employee/{id}/rate")
    public ResponseEntity<List<EmployeeRateDTO>> getEmployeeRate(@PathVariable Integer id) {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTOs(employeeRateFacade.getEmployeeRate(id)));
    }

    @PostMapping("/employee/rate")
    public ResponseEntity<EmployeeRateDTO> addEmployeeRate(@RequestBody CreateEmployeeRateDTO employeeRateDTO)
            throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTO(
                employeeRateFacade.addEmployeeRate(EmployeeRateMapper.mapToEmployeeRateModel(employeeRateDTO))));
    }

    @PutMapping("/employee/rate")
    public ResponseEntity<EmployeeRateDTO> editEmployeeRate(@RequestBody EmployeeRateDTO employeeRateDTO) {
        return ResponseEntity.ok(EmployeeRateMapper.mapToEmployeeRateDTO(
                employeeRateFacade.editEmployeeRate(EmployeeRateMapper.mapToEmployeeRateModel(employeeRateDTO))));
    }



}
