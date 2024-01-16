package com.designofficems.designofficemanagementsystem.repository;

import com.designofficems.designofficemanagementsystem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsByName(String name);

    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE %:searchBy%")
    List<Project> findAllProjectsContainingParam(@Param("searchBy") String searchBy);
}
