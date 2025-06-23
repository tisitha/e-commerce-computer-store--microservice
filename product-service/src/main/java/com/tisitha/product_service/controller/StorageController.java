package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.storage.StorageFilterOptionsDTO;
import com.tisitha.product_service.dto.storage.StorageGetRequestDTO;
import com.tisitha.product_service.dto.storage.StorageRequestDTO;
import com.tisitha.product_service.dto.storage.StorageResponseDTO;
import com.tisitha.product_service.service.StorageService;
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

    @GetMapping("/product/category/storages/get")
    public ResponseEntity<ProductPageSortDto<StorageResponseDTO>> getStorage(@RequestBody StorageGetRequestDTO dto){
        return new ResponseEntity<>(storageService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getStorageType(),dto.getCapacityGB(),dto.getInterfaceType(),dto.getUsageType()),
                HttpStatus.OK);
    }

    @GetMapping("/product/category/storages/filters")
    public ResponseEntity<StorageFilterOptionsDTO> getStorageFilters(){
        return new ResponseEntity<>(storageService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/storages/add")
    public ResponseEntity<StorageResponseDTO> add(@RequestBody StorageRequestDTO dto){
        return new ResponseEntity<>(storageService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/storages/update/{id}")
    public ResponseEntity<StorageResponseDTO> update(@PathVariable UUID id, @RequestBody StorageRequestDTO dto){
        return new ResponseEntity<>(storageService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/storages/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        storageService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
