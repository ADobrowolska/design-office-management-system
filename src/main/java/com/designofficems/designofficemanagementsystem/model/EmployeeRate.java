package com.designofficems.designofficemanagementsystem.model;

import com.designofficems.designofficemanagementsystem.util.CategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_rate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(name = "category", columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    private double rate;

    @Column(nullable = false)
    private String currency;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public static Employee ofId(Integer employeeId) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        return employee;
    }
}
