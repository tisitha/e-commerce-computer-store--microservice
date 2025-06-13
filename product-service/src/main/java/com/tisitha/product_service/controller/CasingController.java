package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.casing.CasingFilterOptionsDTO;
import com.tisitha.product_service.dto.casing.CasingGetRequestDTO;
import com.tisitha.product_service.dto.casing.CasingRequestDTO;
import com.tisitha.product_service.dto.casing.CasingResponseDTO;
import com.tisitha.product_service.service.CasingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("category/casings")
public class CasingController {

    private final CasingService casingService;

    public CasingController(CasingService casingService) {
        this.casingService = casingService;
    }

    @GetMapping("/get")
    public ResponseEntity<ProductPageSortDto<CasingResponseDTO>> getCasing(@RequestBody CasingGetRequestDTO dto){
        return new ResponseEntity<>(casingService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getCaseType(),dto.getMaxGPULength(),dto.getIncludedFans()),
                HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<CasingFilterOptionsDTO> getCasingFilters(){
        return new ResponseEntity<>(casingService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<CasingResponseDTO> add(@RequestBody CasingRequestDTO dto){
        return new ResponseEntity<>(casingService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<CasingResponseDTO> update(@PathVariable UUID id, @RequestBody CasingRequestDTO dto){
        return new ResponseEntity<>(casingService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        casingService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
