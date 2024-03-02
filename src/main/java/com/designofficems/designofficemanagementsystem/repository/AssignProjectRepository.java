package com.designofficems.designofficemanagementsystem.repository;

import com.designofficems.designofficemanagementsystem.model.AssignProject;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignProjectRepository extends JpaRepository<AssignProject, Integer> {

    List<AssignProject> findAllByProject(Project project);

    List<AssignProject> findAllByEmployee(Employee employee);

    boolean existsByProjectIdAndEmployeeId(Integer projectId, Integer employeeId);
}
