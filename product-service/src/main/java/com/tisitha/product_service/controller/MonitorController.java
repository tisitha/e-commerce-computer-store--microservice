package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.monitor.MonitorFilterOptionsDTO;
import com.tisitha.product_service.dto.monitor.MonitorGetRequestDTO;
import com.tisitha.product_service.dto.monitor.MonitorRequestDTO;
import com.tisitha.product_service.dto.monitor.MonitorResponseDTO;
import com.tisitha.product_service.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping("/category/monitors/get")
    public ResponseEntity<ProductPageSortDto<MonitorResponseDTO>> getMonitor(@RequestBody MonitorGetRequestDTO dto){
        return new ResponseEntity<>(monitorService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getDisplayResolution(),dto.getRefreshRateHz(),dto.getResponseTimeMs(),dto.getPanelType(),dto.getAspectRatio(),dto.getAdaptiveSyncTechnology()),
                HttpStatus.OK);
    }

    @GetMapping("/category/monitors/filters")
    public ResponseEntity<MonitorFilterOptionsDTO> getMonitorFilters(){
        return new ResponseEntity<>(monitorService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/monitors/add")
    public ResponseEntity<MonitorResponseDTO> add(@RequestBody MonitorRequestDTO dto){
        return new ResponseEntity<>(monitorService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/monitors/update/{id}")
    public ResponseEntity<MonitorResponseDTO> update(@PathVariable UUID id, @RequestBody MonitorRequestDTO dto){
        return new ResponseEntity<>(monitorService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/monitors/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        monitorService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
