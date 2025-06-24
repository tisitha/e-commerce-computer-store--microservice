package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.laptop.LaptopFilterOptionsDTO;
import com.tisitha.product_service.dto.laptop.LaptopGetRequestDTO;
import com.tisitha.product_service.dto.laptop.LaptopRequestDTO;
import com.tisitha.product_service.dto.laptop.LaptopResponseDTO;
import com.tisitha.product_service.service.LaptopService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class LaptopController {

    private final LaptopService laptopService;

    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @Operation(summary = "Get product data category=laptops")
    @GetMapping("/product/category/laptops/get")
    public ResponseEntity<ProductPageSortDto<LaptopResponseDTO>> getLaptop(@RequestBody LaptopGetRequestDTO dto){
        return new ResponseEntity<>(laptopService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getProcessorBrand(),dto.getProcessorSeries(),dto.getRamCapacity(),dto.getStorageCapacity(),dto.getDisplayResolution(),dto.getOperatingSystem(),dto.getGraphicsCardType(),dto.getFeaturesIncluded()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for laptops")
    @GetMapping("/product/category/laptops/filters")
    public ResponseEntity<LaptopFilterOptionsDTO> getLaptopFilters(){
        return new ResponseEntity<>(laptopService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=laptops")
    @PostMapping("/admin/category/laptops/add")
    public ResponseEntity<LaptopResponseDTO> add(@RequestBody LaptopRequestDTO dto){
        return new ResponseEntity<>(laptopService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=laptops")
    @PutMapping("/admin/category/laptops/update/{id}")
    public ResponseEntity<LaptopResponseDTO> update(@PathVariable UUID id, @RequestBody LaptopRequestDTO dto){
        return new ResponseEntity<>(laptopService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=laptops")
    @DeleteMapping("/admin/category/laptops/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        laptopService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
