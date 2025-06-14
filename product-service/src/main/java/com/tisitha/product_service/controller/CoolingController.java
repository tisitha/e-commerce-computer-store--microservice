package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.cooling.CoolingFilterOptionsDTO;
import com.tisitha.product_service.dto.cooling.CoolingGetRequestDTO;
import com.tisitha.product_service.dto.cooling.CoolingRequestDTO;
import com.tisitha.product_service.dto.cooling.CoolingResponseDTO;
import com.tisitha.product_service.service.CoolingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class CoolingController {

    private final CoolingService coolingService;

    public CoolingController(CoolingService coolingService) {
        this.coolingService = coolingService;
    }

    @GetMapping("/category/cooling/get")
    public ResponseEntity<ProductPageSortDto<CoolingResponseDTO>> getCooling(@RequestBody CoolingGetRequestDTO dto){
        return new ResponseEntity<>(coolingService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getCoolingType(),dto.getSocketCompatibility(),dto.getFanSize(),dto.getRgbLighting()),
                HttpStatus.OK);
    }

    @GetMapping("/category/cooling/filters")
    public ResponseEntity<CoolingFilterOptionsDTO> getCoolingFilters(){
        return new ResponseEntity<>(coolingService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/cooling/add")
    public ResponseEntity<CoolingResponseDTO> add(@RequestBody CoolingRequestDTO dto){
        return new ResponseEntity<>(coolingService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/cooling/update/{id}")
    public ResponseEntity<CoolingResponseDTO> update(@PathVariable UUID id, @RequestBody CoolingRequestDTO dto){
        return new ResponseEntity<>(coolingService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/cooling/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        coolingService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
