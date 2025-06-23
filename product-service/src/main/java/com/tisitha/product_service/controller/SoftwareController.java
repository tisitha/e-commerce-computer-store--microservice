package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.software.SoftwareFilterOptionsDTO;
import com.tisitha.product_service.dto.software.SoftwareGetRequestDTO;
import com.tisitha.product_service.dto.software.SoftwareRequestDTO;
import com.tisitha.product_service.dto.software.SoftwareResponseDTO;
import com.tisitha.product_service.service.SoftwareService;
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

    @GetMapping("/product/category/software/get")
    public ResponseEntity<ProductPageSortDto<SoftwareResponseDTO>> getSoftware(@RequestBody SoftwareGetRequestDTO dto){
        return new ResponseEntity<>(softwareService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getYears(),dto.getUses()),
                HttpStatus.OK);
    }

    @GetMapping("/product/category/software/filters")
    public ResponseEntity<SoftwareFilterOptionsDTO> getSoftwareFilters(){
        return new ResponseEntity<>(softwareService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/software/add")
    public ResponseEntity<SoftwareResponseDTO> add(@RequestBody SoftwareRequestDTO dto){
        return new ResponseEntity<>(softwareService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/software/update/{id}")
    public ResponseEntity<SoftwareResponseDTO> update(@PathVariable UUID id, @RequestBody SoftwareRequestDTO dto){
        return new ResponseEntity<>(softwareService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/software/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        softwareService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
