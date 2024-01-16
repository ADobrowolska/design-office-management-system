package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.project.CreateProjectDTO;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectDTO;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectMapper;
import com.designofficems.designofficemanagementsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> addProject(@RequestBody CreateProjectDTO projectDTO) throws InstanceAlreadyExistsException {
        ProjectDTO receivedProjectDTO = ProjectMapper.mapToProjectDTO(projectService.addProject(ProjectMapper.mapToProjectModel(projectDTO)));
        return ResponseEntity.ok(receivedProjectDTO);
    }

    @PutMapping("/projects")
    public ResponseEntity<ProjectDTO> editProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO receivedProjectDTO = ProjectMapper.mapToProjectDTO(projectService.editProject(ProjectMapper.mapToProjectModel(projectDTO)));
        return ResponseEntity.ok(receivedProjectDTO);
    }



}
