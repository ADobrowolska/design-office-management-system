package com.designofficems.designofficemanagementsystem.model;

import com.designofficems.designofficemanagementsystem.dto.cost.DailyCostDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProjectCost {

    private BigDecimal total;
    private List<DailyCostDTO> dailyCosts;

}
