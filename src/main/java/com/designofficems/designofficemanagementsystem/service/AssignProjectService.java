package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.AssignProject;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.AssignProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignProjectService {

    private final AssignProjectRepository assignProjectRepository;

    @Autowired
    public AssignProjectService(AssignProjectRepository assignProjectRepository) {
        this.assignProjectRepository = assignProjectRepository;
    }

    public List<Employee> getEmployeesByProject(Project project) {
        List<AssignProject> allByProject = assignProjectRepository.findAllByProject(project);
        return allByProject.stream().map(AssignProject::getEmployee).collect(Collectors.toList());
    }

    public List<Project> getProjectsByEmployee(Employee employee) {
        List<AssignProject> allByEmployee = assignProjectRepository.findAllByEmployee(employee);
        return allByEmployee.stream().map(AssignProject::getProject).collect(Collectors.toList());
    }

    @Transactional
    public AssignProject assignEmployeeToProject(Project project, Employee employee) {
        AssignProject assignProject = new AssignProject();
        assignProject.setProject(project);
        assignProject.setEmployee(employee);
        return assignProjectRepository.save(assignProject);
    }

    public boolean checkIfEmployeeIsAssignedToProject(Integer projectId, Integer employeeId) {
        return assignProjectRepository.existsByProjectIdAndEmployeeId(projectId, employeeId);
    }
}
