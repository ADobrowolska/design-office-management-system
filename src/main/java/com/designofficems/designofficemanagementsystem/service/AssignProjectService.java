package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.AssignProject;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.AssignProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignProjectService {

    private final AssignProjectRepository assignProjectRepository;

    @Autowired
    public AssignProjectService(AssignProjectRepository assignProjectRepository) {
        this.assignProjectRepository = assignProjectRepository;
    }

    public List<Employee> getEmployeeByProject(Project project) {
        return assignProjectRepository.findAllByProject(project);
    }

    @Transactional
    public AssignProject assignEmployeeToProject(Project project, Employee employee) {
        AssignProject assignProject = new AssignProject();
        assignProject.setProject(project);
        assignProject.setEmployee(employee);
        return assignProjectRepository.save(assignProject);
    }
}
