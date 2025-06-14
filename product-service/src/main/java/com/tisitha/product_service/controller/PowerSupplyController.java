package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyFilterOptionsDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyGetRequestDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyRequestDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyResponseDTO;
import com.tisitha.product_service.service.PowerSupplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class PowerSupplyController {

    private final PowerSupplyService powerSupplyService;

    public PowerSupplyController(PowerSupplyService powerSupplyService) {
        this.powerSupplyService = powerSupplyService;
    }

    @GetMapping("/category/powerSupplys/get")
    public ResponseEntity<ProductPageSortDto<PowerSupplyResponseDTO>> getPowerSupply(@RequestBody PowerSupplyGetRequestDTO dto){
        return new ResponseEntity<>(powerSupplyService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getWattageOutput(),dto.getCertificationRating(),dto.getFormFactor(),dto.getModularityType()),
                HttpStatus.OK);
    }

    @GetMapping("/category/powerSupplys/filters")
    public ResponseEntity<PowerSupplyFilterOptionsDTO> getPowerSupplyFilters(){
        return new ResponseEntity<>(powerSupplyService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/powerSupplys/add")
    public ResponseEntity<PowerSupplyResponseDTO> add(@RequestBody PowerSupplyRequestDTO dto){
        return new ResponseEntity<>(powerSupplyService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/powerSupplys/update/{id}")
    public ResponseEntity<PowerSupplyResponseDTO> update(@PathVariable UUID id, @RequestBody PowerSupplyRequestDTO dto){
        return new ResponseEntity<>(powerSupplyService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/powerSupplys/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        powerSupplyService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
