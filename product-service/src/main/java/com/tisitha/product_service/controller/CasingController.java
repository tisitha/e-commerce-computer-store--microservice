package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.casing.CasingFilterOptionsDTO;
import com.tisitha.product_service.dto.casing.CasingGetRequestDTO;
import com.tisitha.product_service.dto.casing.CasingRequestDTO;
import com.tisitha.product_service.dto.casing.CasingResponseDTO;
import com.tisitha.product_service.service.CasingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class CasingController {

    private final CasingService casingService;

    public CasingController(CasingService casingService) {
        this.casingService = casingService;
    }

    @Operation(summary = "Get a product category=casings")
    @GetMapping("/product/category/casings/item/{id}")
    public ResponseEntity<CasingResponseDTO> getProduct(@PathVariable UUID id){
        return new ResponseEntity<>(casingService.getProduct(id),HttpStatus.OK);
    }

    @Operation(summary = "Get products of category=casings")
    @GetMapping("/product/category/casings/get")
    public ResponseEntity<ProductPageSortDto<CasingResponseDTO>> getCasing(@Valid @RequestBody CasingGetRequestDTO dto){
        return new ResponseEntity<>(casingService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getCaseType(),dto.getMaxGPULength(),dto.getIncludedFans()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for casings")
    @GetMapping("/product/category/casings/filters")
    public ResponseEntity<CasingFilterOptionsDTO> getCasingFilters(){
        return new ResponseEntity<>(casingService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=casings")
    @PostMapping("/admin/product/category/casings/add")
    public ResponseEntity<CasingResponseDTO> add(@Valid @RequestBody CasingRequestDTO dto){
        return new ResponseEntity<>(casingService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=casings")
    @PutMapping("/admin/product/category/casings/update/{id}")
    public ResponseEntity<CasingResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody CasingRequestDTO dto){
        return new ResponseEntity<>(casingService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=casings")
    @DeleteMapping("/admin/product/category/casings/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        casingService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
