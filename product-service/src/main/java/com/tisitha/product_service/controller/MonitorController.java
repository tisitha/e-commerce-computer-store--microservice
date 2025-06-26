package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.monitor.MonitorFilterOptionsDTO;
import com.tisitha.product_service.dto.monitor.MonitorGetRequestDTO;
import com.tisitha.product_service.dto.monitor.MonitorRequestDTO;
import com.tisitha.product_service.dto.monitor.MonitorResponseDTO;
import com.tisitha.product_service.service.MonitorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @Operation(summary = "Get product data category=monitors")
    @GetMapping("/product/category/monitors/get")
    public ResponseEntity<ProductPageSortDto<MonitorResponseDTO>> getMonitor(@Valid @RequestBody MonitorGetRequestDTO dto){
        return new ResponseEntity<>(monitorService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getDisplayResolution(),dto.getRefreshRateHz(),dto.getResponseTimeMs(),dto.getPanelType(),dto.getAspectRatio(),dto.getAdaptiveSyncTechnology()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for monitors")
    @GetMapping("/product/category/monitors/filters")
    public ResponseEntity<MonitorFilterOptionsDTO> getMonitorFilters(){
        return new ResponseEntity<>(monitorService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=monitors")
    @PostMapping("/admin/product/category/monitors/add")
    public ResponseEntity<MonitorResponseDTO> add(@Valid @RequestBody MonitorRequestDTO dto){
        return new ResponseEntity<>(monitorService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=monitors")
    @PutMapping("/admin/product/category/monitors/update/{id}")
    public ResponseEntity<MonitorResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody MonitorRequestDTO dto){
        return new ResponseEntity<>(monitorService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=monitors")
    @DeleteMapping("/admin/product/category/monitors/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        monitorService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
