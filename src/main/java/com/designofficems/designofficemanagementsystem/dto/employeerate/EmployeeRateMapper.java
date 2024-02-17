package com.designofficems.designofficemanagementsystem.dto.employeerate;

import com.designofficems.designofficemanagementsystem.model.EmployeeRate;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeRateMapper {

    public static EmployeeRateDTO mapToEmployeeRateDTO(EmployeeRate employeeRate) {
        return EmployeeRateDTO.builder()
                .id(employeeRate.getId())
                .name(employeeRate.getName())
                .category(employeeRate.getCategory())
                .rate(employeeRate.getRate())
                .employeeId(employeeRate.getEmployee().getId())
                .currency(employeeRate.getCurrency())
                .build();
    }

    public static EmployeeRate mapToEmployeeRateModel(EmployeeRateDTO employeeRateDTO) {
        return EmployeeRate.builder()
                .id(employeeRateDTO.getId())
                .name(employeeRateDTO.getName())
                .category(employeeRateDTO.getCategory())
                .rate(employeeRateDTO.getRate())
                .employee(EmployeeRate.ofId(employeeRateDTO.getEmployeeId()))
                .currency(employeeRateDTO.getCurrency())
                .build();
    }

    public static EmployeeRate mapToEmployeeRateModel(CreateEmployeeRateDTO employeeRateDTO) {
        return EmployeeRate.builder()
                .name(employeeRateDTO.getName())
                .category(employeeRateDTO.getCategory())
                .employee(EmployeeRate.ofId(employeeRateDTO.getEmployeeId()))
                .currency(employeeRateDTO.getCurrency())
                .rate(employeeRateDTO.getRate())
                .build();
    }

    public static List<EmployeeRateDTO> mapToEmployeeRateDTOs(List<EmployeeRate> employeeRates) {
        return employeeRates.stream()
                .map(EmployeeRateMapper::mapToEmployeeRateDTO)
                .collect(Collectors.toList());
    }

    public static List<EmployeeRate> mapToEmployeeRates(List<EmployeeRateDTO> employeeRateDTOs) {
        return employeeRateDTOs.stream()
                .map(EmployeeRateMapper::mapToEmployeeRateModel)
                .collect(Collectors.toList());
    }

}
