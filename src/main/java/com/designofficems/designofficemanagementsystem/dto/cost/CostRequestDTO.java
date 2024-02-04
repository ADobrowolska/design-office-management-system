package com.designofficems.designofficemanagementsystem.dto.cost;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CostRequestDTO {

    private LocalDate occurrenceDate;
    private Long quantity;
    private Integer projectId;

}
