package com.designofficems.designofficemanagementsystem.repository;


import com.designofficems.designofficemanagementsystem.model.Cost;
import com.designofficems.designofficemanagementsystem.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface CostRepository extends JpaRepository<Cost, Integer> {

    List<Cost> findAllByEmployeeRateEmployee(Employee employee, Sort sort);

    List<Cost> findAllByEmployeeRateEmployeeAndOccurrenceDate(Employee employee, Instant date);

    void deleteByIdAndEmployeeRateEmployee(Integer id, Employee employee);

    List<Cost> findAllByProjectIdAndOccurrenceDateGreaterThanEqualAndOccurrenceDateLessThanEqual(
            int projectId, Instant startDate, Instant endDate);
}

