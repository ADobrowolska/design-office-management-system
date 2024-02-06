package com.designofficems.designofficemanagementsystem.repository;


import com.designofficems.designofficemanagementsystem.model.Cost;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostRepository extends JpaRepository<Cost, Integer> {


    List<Cost> findAllByEmployeeRate(EmployeeRate employeeRate);
}
