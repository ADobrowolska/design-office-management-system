package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.assignproject.AssignProjectDTO;
import com.designofficems.designofficemanagementsystem.dto.project.*;
import com.designofficems.designofficemanagementsystem.facade.ProjectFacade;
import com.designofficems.designofficemanagementsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectFacade projectFacade;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectFacade projectFacade) {
        this.projectService = projectService;
        this.projectFacade = projectFacade;
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> addProject(@RequestBody CreateProjectDTO projectDTO)
            throws InstanceAlreadyExistsException {
        ProjectDTO receivedProjectDTO = ProjectMapper.mapToProjectDTO(
                projectService.addProject(ProjectMapper.mapToProjectModel(projectDTO)));
        return ResponseEntity.ok(receivedProjectDTO);
    }

    @PutMapping("/projects")
    public ResponseEntity<ProjectDTO> editProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO receivedProjectDTO = ProjectMapper.mapToProjectDTO(
                projectService.editProject(ProjectMapper.mapToProjectModel(projectDTO)));
        return ResponseEntity.ok(receivedProjectDTO);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getProjects() {
        List<ProjectDTO> receivedProjectDTOs = ProjectMapper.mapToProjectDTOs(projectService.getProjects());
        return ResponseEntity.ok(receivedProjectDTOs);
    }

    @GetMapping("/projects/param")
    public ResponseEntity<List<ProjectDTO>> getProjectByParam(@RequestParam(required = true) String searchBy) {
        List<ProjectDTO> receivedProjectDTOs = ProjectMapper.mapToProjectDTOs(
                projectService.getProjectsByParam(searchBy));
        return ResponseEntity.ok(receivedProjectDTOs);
    }

    @GetMapping("/projects/{id}/employees")
    public ResponseEntity<ProjectEmployeeDTO> getProjectWithEmployees(@PathVariable int id) {
        return ResponseEntity.ok(projectFacade.getProjectWithEmployees(id));
    }

    @GetMapping("/projects/employees")
    public ResponseEntity<List<ProjectEmployeeDTO>> getProjectsWithEmployees() {
        return ResponseEntity.ok(projectFacade.getProjectsWithEmployees());
    }

    @GetMapping("/projects/{id}/cost")
    public ResponseEntity<ProjectCostDTO> getCostPerProject(@PathVariable int id,
                                                            @RequestParam LocalDate startDate,
                                                            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(projectFacade.getCostPerProject(id, startDate, endDate));
    }

    @PutMapping("/projects/assign")
    public ResponseEntity<AssignProjectDTO> editProjectEmployee(@RequestBody ProjectEmployeeDTO projectDTO,
                                                                @RequestParam(required = false) Integer employeeId) throws InstanceAlreadyExistsException {
        AssignProjectDTO receivedAssignProjectDTO = projectFacade.editProjectEmployee(
                ProjectMapper.mapToProjectModel(projectDTO), employeeId);
        return ResponseEntity.ok(receivedAssignProjectDTO);
    }



}
