package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.department.DepartmentDTO;
import com.designofficems.designofficemanagementsystem.dto.department.DepartmentEmployeeDTO;
import com.designofficems.designofficemanagementsystem.dto.department.DepartmentMapper;
import com.designofficems.designofficemanagementsystem.facade.DepartmentFacade;
import com.designofficems.designofficemanagementsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentFacade departmentFacade;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentFacade departmentFacade) {
        this.departmentService = departmentService;
        this.departmentFacade = departmentFacade;
    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
        List<DepartmentDTO> receivedDepartmentDTOs = DepartmentMapper.mapToDepartmentDTOs(departmentService.getDepartments());
        return ResponseEntity.ok(receivedDepartmentDTOs);
    }

    @GetMapping("/departments/param")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByParam(@RequestParam(required = true) String searchBy) {
        List<DepartmentDTO> receivedDepartmentDTOs = DepartmentMapper.mapToDepartmentDTOs(departmentService.getDepartmentsByParam(searchBy));
        return ResponseEntity.ok(receivedDepartmentDTOs);
    }

    @GetMapping("/departments/{id}/employees")
    public ResponseEntity<DepartmentEmployeeDTO> getDepartmentWithEmployees(@PathVariable Integer id) {
        return ResponseEntity.ok(departmentFacade.getDepartmentEmployee(id));
    }

    @GetMapping("/departments/employees")
    public ResponseEntity<List<DepartmentEmployeeDTO>> getDepartmentsWithEmployees() {
        return ResponseEntity.ok(departmentFacade.getDepartmentsWithEmployees());
    }


}
