package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.cost.CostMapper;
import com.designofficems.designofficemanagementsystem.dto.cost.CostRequestDTO;
import com.designofficems.designofficemanagementsystem.dto.cost.CostResponseDTO;
import com.designofficems.designofficemanagementsystem.facade.CostFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    public ResponseEntity<List<CostResponseDTO>> getCosts() {
//
//    }
//
//    public ResponseEntity<CostResponseDTO> getCostByDay(@RequestParam LocalDate date) {
//
//    }
//
//    public ResponseEntity<Void> deleteCost(@PathVariable Integer id) {
//
//    }

}
