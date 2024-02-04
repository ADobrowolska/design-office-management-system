package com.designofficems.designofficemanagementsystem.repository;


import com.designofficems.designofficemanagementsystem.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends JpaRepository<Cost, Integer> {

}
