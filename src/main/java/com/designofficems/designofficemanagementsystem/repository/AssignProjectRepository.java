package com.designofficems.designofficemanagementsystem.repository;

import com.designofficems.designofficemanagementsystem.model.AssignProject;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignProjectRepository extends JpaRepository<AssignProject, Integer> {

    List<Project> findAllByEmployee(Employee employee);

    List<Employee> findAllByProject(Project project);
}
