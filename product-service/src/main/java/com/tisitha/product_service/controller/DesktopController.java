package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.desktop.DesktopFilterOptionsDTO;
import com.tisitha.product_service.dto.desktop.DesktopGetRequestDTO;
import com.tisitha.product_service.dto.desktop.DesktopRequestDTO;
import com.tisitha.product_service.dto.desktop.DesktopResponseDTO;
import com.tisitha.product_service.service.DesktopService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class DesktopController {

    private final DesktopService desktopService;

    public DesktopController(DesktopService desktopService) {
        this.desktopService = desktopService;
    }

    @Operation(summary = "Get product data category=desktops")
    @GetMapping("/product/category/desktops/get")
    public ResponseEntity<ProductPageSortDto<DesktopResponseDTO>> getDesktop(@RequestBody DesktopGetRequestDTO dto){
        return new ResponseEntity<>(desktopService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getProductType(),dto.getProcessorBrand(),dto.getProcessorSeries(),dto.getGpuManufacturer(),dto.getGpuSeries(),dto.getRamCapacity(),dto.getStorageType(),dto.getStorageCapacity(),dto.getOperatingSystem()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for desktops")
    @GetMapping("/product/category/desktops/filters")
    public ResponseEntity<DesktopFilterOptionsDTO> getDesktopFilters(){
        return new ResponseEntity<>(desktopService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=desktops")
    @PostMapping("/admin/product/category/desktops/add")
    public ResponseEntity<DesktopResponseDTO> add(@RequestBody DesktopRequestDTO dto){
        return new ResponseEntity<>(desktopService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=desktops")
    @PutMapping("/admin/product/category/desktops/update/{id}")
    public ResponseEntity<DesktopResponseDTO> update(@PathVariable UUID id, @RequestBody DesktopRequestDTO dto){
        return new ResponseEntity<>(desktopService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=desktops")
    @DeleteMapping("/admin/product/category/desktops/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        desktopService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
