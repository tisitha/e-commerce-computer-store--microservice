package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyFilterOptionsDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyGetRequestDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyRequestDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyResponseDTO;
import com.tisitha.product_service.service.PowerSupplyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class PowerSupplyController {

    private final PowerSupplyService powerSupplyService;

    public PowerSupplyController(PowerSupplyService powerSupplyService) {
        this.powerSupplyService = powerSupplyService;
    }

    @Operation(summary = "Get product data category=power")
    @GetMapping("/product/category/power/get")
    public ResponseEntity<ProductPageSortDto<PowerSupplyResponseDTO>> getPowerSupply(@RequestBody PowerSupplyGetRequestDTO dto){
        return new ResponseEntity<>(powerSupplyService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getWattageOutput(),dto.getCertificationRating(),dto.getFormFactor(),dto.getModularityType()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for power")
    @GetMapping("/product/category/power/filters")
    public ResponseEntity<PowerSupplyFilterOptionsDTO> getPowerSupplyFilters(){
        return new ResponseEntity<>(powerSupplyService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=power")
    @PostMapping("/admin/product/category/power/add")
    public ResponseEntity<PowerSupplyResponseDTO> add(@RequestBody PowerSupplyRequestDTO dto){
        return new ResponseEntity<>(powerSupplyService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=power")
    @PutMapping("/admin/product/category/power/update/{id}")
    public ResponseEntity<PowerSupplyResponseDTO> update(@PathVariable UUID id, @RequestBody PowerSupplyRequestDTO dto){
        return new ResponseEntity<>(powerSupplyService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=power")
    @DeleteMapping("/admin/product/category/power/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        powerSupplyService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
