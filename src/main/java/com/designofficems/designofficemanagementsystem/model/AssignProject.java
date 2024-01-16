package com.designofficems.designofficemanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "assign_project")
@Data
public class AssignProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
