package com.designofficems.designofficemanagementsystem.repository;

import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRateRepository extends JpaRepository<EmployeeRate, Integer> {

    List<EmployeeRate> findAllByEmployee(Employee employee);


}
