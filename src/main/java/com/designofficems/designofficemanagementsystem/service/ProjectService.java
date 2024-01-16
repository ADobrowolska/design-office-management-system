package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceAlreadyExistsException;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project) throws InstanceAlreadyExistsException {
        if (!projectRepository.existsByName(project.getName())) {
            return projectRepository.save(project);
        } else {
            throw new InstanceAlreadyExistsException();
        }
    }

    @Transactional
    public Project editProject(Project project) {
        Project existed = getProject(project.getId());
        existed.setName(project.getName());
        existed.setBudget(project.getBudget());
        existed.setDescription(project.getDescription());
        return projectRepository.save(existed);
    }

    public Project getProject(int id) {
        return projectRepository.findById(id).orElseThrow();
    }
}
