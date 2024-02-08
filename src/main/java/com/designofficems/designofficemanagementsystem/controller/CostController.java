package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.cost.CostDTO;
import com.designofficems.designofficemanagementsystem.dto.cost.CostMapper;
import com.designofficems.designofficemanagementsystem.dto.cost.CostRequestDTO;
import com.designofficems.designofficemanagementsystem.dto.cost.CostResponseDTO;
import com.designofficems.designofficemanagementsystem.facade.CostFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CostController {

    private final CostFacade costFacade;

    @Autowired
    public CostController(CostFacade costFacade) {
        this.costFacade = costFacade;
    }

    @PostMapping("/costs")
    public ResponseEntity<CostResponseDTO> addCost(@RequestBody CostRequestDTO costRequest) {
        CostResponseDTO cost = CostMapper.mapToCostResponseDTO(costFacade.add(
                costRequest.getProjectId(),
                costRequest.getQuantity(),
                costRequest.getOccurrenceDate()));
        return ResponseEntity.ok(cost);
    }

    @GetMapping("/costs")
    public ResponseEntity<List<CostDTO>> getAllCosts() {
        List<CostDTO> costDTOs = CostMapper.mapToCostDTOs(costFacade.getCosts());
        return ResponseEntity.ok(costDTOs);
    }

    @GetMapping("/costs/param")
    public ResponseEntity<List<CostDTO>> getCostByDay(@RequestParam LocalDate date) {
        List<CostDTO> costDTOs = CostMapper.mapToCostDTOs(costFacade.getCostsByDay(date));
        return ResponseEntity.ok(costDTOs);
    }
//
//    public ResponseEntity<Void> deleteCost(@PathVariable Integer id) {
//
//    }

}
