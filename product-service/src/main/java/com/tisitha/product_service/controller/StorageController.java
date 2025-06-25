package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.storage.StorageFilterOptionsDTO;
import com.tisitha.product_service.dto.storage.StorageGetRequestDTO;
import com.tisitha.product_service.dto.storage.StorageRequestDTO;
import com.tisitha.product_service.dto.storage.StorageResponseDTO;
import com.tisitha.product_service.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Operation(summary = "Get product data category=storages")
    @GetMapping("/product/category/storages/get")
    public ResponseEntity<ProductPageSortDto<StorageResponseDTO>> getStorage(@RequestBody StorageGetRequestDTO dto){
        return new ResponseEntity<>(storageService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getStorageType(),dto.getCapacityGB(),dto.getInterfaceType(),dto.getUsageType()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for storages")
    @GetMapping("/product/category/storages/filters")
    public ResponseEntity<StorageFilterOptionsDTO> getStorageFilters(){
        return new ResponseEntity<>(storageService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=storages")
    @PostMapping("/admin/product/category/storages/add")
    public ResponseEntity<StorageResponseDTO> add(@RequestBody StorageRequestDTO dto){
        return new ResponseEntity<>(storageService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=storages")
    @PutMapping("/admin/product/category/storages/update/{id}")
    public ResponseEntity<StorageResponseDTO> update(@PathVariable UUID id, @RequestBody StorageRequestDTO dto){
        return new ResponseEntity<>(storageService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=storages")
    @DeleteMapping("/admin/product/category/storages/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        storageService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
