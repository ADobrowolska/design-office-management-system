package com.designofficems.designofficemanagementsystem.repository;

import com.designofficems.designofficemanagementsystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("SELECT d FROM Department d WHERE LOWER(d.name) LIKE %:searchBy%")
    List<Department> findAllDepartmentsContainingParam(@Param("searchBy") String searchBy);
}
