package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.software.SoftwareFilterOptionsDTO;
import com.tisitha.product_service.dto.software.SoftwareGetRequestDTO;
import com.tisitha.product_service.dto.software.SoftwareRequestDTO;
import com.tisitha.product_service.dto.software.SoftwareResponseDTO;
import com.tisitha.product_service.service.SoftwareService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class SoftwareController {

    private final SoftwareService softwareService;

    public SoftwareController(SoftwareService softwareService) {
        this.softwareService = softwareService;
    }

    @Operation(summary = "Get product data category=software")
    @GetMapping("/product/category/software/get")
    public ResponseEntity<ProductPageSortDto<SoftwareResponseDTO>> getSoftware(@RequestBody SoftwareGetRequestDTO dto){
        return new ResponseEntity<>(softwareService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getYears(),dto.getUses()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for software")
    @GetMapping("/product/category/software/filters")
    public ResponseEntity<SoftwareFilterOptionsDTO> getSoftwareFilters(){
        return new ResponseEntity<>(softwareService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=software")
    @PostMapping("/admin/product/category/software/add")
    public ResponseEntity<SoftwareResponseDTO> add(@RequestBody SoftwareRequestDTO dto){
        return new ResponseEntity<>(softwareService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=software")
    @PutMapping("/admin/product/category/software/update/{id}")
    public ResponseEntity<SoftwareResponseDTO> update(@PathVariable UUID id, @RequestBody SoftwareRequestDTO dto){
        return new ResponseEntity<>(softwareService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=software")
    @DeleteMapping("/admin/product/category/software/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        softwareService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
