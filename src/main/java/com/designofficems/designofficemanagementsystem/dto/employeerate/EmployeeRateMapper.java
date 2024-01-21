package com.designofficems.designofficemanagementsystem.dto.employeerate;

import com.designofficems.designofficemanagementsystem.model.EmployeeRate;

public class EmployeeRateMapper {

    public static EmployeeRateDTO mapToEmployeeRateDTO(EmployeeRate employeeRate) {
        return EmployeeRateDTO.builder()
                .id(employeeRate.getId())
                .name(employeeRate.getName())
                .category(employeeRate.getCategory())
                .rate(employeeRate.getRate())
                .employeeId(employeeRate.getEmployee().getId())
                .build();
    }

    public static EmployeeRate mapToEmployeeRateModel(EmployeeRateDTO employeeRateDTO) {
        return EmployeeRate.builder()
                .id(employeeRateDTO.getId())
                .name(employeeRateDTO.getName())
                .category(employeeRateDTO.getCategory())
                .rate(employeeRateDTO.getRate())
                .employee(EmployeeRate.ofId(employeeRateDTO.getEmployeeId()))
                .build();
    }

}
