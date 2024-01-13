package com.designofficems.designofficemanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cost")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Instant occurrenceDate;
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "employee_rate_id")
    private EmployeeRate employeeRate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}
