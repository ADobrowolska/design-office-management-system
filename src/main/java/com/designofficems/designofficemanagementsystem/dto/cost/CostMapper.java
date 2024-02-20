package com.designofficems.designofficemanagementsystem.dto.cost;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateMapper;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectMapper;
import com.designofficems.designofficemanagementsystem.model.Cost;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.util.DateTimeUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import static com.designofficems.designofficemanagementsystem.service.CostService.getSubtotal;
import static com.designofficems.designofficemanagementsystem.service.CostService.getTotal;

public class CostMapper {

    public static CostRequestDTO mapToCostRequestDTO(Cost cost) {
        return CostRequestDTO.builder()
                .projectId(cost.getProject().getId())
                .occurrenceDate(LocalDate.ofInstant(cost.getOccurrenceDate(), ZoneId.systemDefault()))
                .quantity(cost.getQuantity())
                .build();
    }

    public static Cost mapToCostModel(CostRequestDTO costRequest) {
        return Cost.builder()
                .occurrenceDate(costRequest.getOccurrenceDate().atStartOfDay().toInstant(ZoneOffset.UTC))
                .quantity(costRequest.getQuantity())
                .project(Project.ofIf(costRequest.getProjectId()))
                .build();
    }

    public static CostDTO mapToCostDTO(Cost cost) {
        return CostDTO.builder()
                .id(cost.getId())
                .occurrenceDate(DateTimeUtils.toLocalDate(cost.getOccurrenceDate()))
                .quantity(cost.getQuantity())
                .subtotal(getSubtotal(cost))
                .employeeRate(EmployeeRateMapper.mapToEmployeeRateDTO(cost.getEmployeeRate()))
                .build();
    }

    public static CostResponseDTO mapToCostResponseDTO(List<Cost> costs) {
        Cost cost = costs.stream().findFirst().orElseThrow();
        return CostResponseDTO.builder()
                .project(ProjectMapper.mapToProjectDTO(cost.getProject()))
                .occurrenceDate(LocalDate.ofInstant(cost.getOccurrenceDate(), ZoneId.systemDefault()))
                .employee(EmployeeMapper.mapToEmployeeDTO(cost.getEmployeeRate().getEmployee()))
                .total(getTotal(costs))
                .entries(costs.stream().map(CostMapper::mapToCostDTO).collect(Collectors.toList()))
                .build();
    }

    public static List<CostDTO> mapToCostDTOs(List<Cost> costs) {
        return costs.stream()
                .map(CostMapper::mapToCostDTO)
                .collect(Collectors.toList());
    }
}
