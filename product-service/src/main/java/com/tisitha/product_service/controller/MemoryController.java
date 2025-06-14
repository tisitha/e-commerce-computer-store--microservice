package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.memory.MemoryFilterOptionsDTO;
import com.tisitha.product_service.dto.memory.MemoryGetRequestDTO;
import com.tisitha.product_service.dto.memory.MemoryRequestDTO;
import com.tisitha.product_service.dto.memory.MemoryResponseDTO;
import com.tisitha.product_service.service.MemoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class MemoryController {

    private final MemoryService memoryService;

    public MemoryController(MemoryService memoryService) {
        this.memoryService = memoryService;
    }

    @GetMapping("/category/memorys/get")
    public ResponseEntity<ProductPageSortDto<MemoryResponseDTO>> getMemory(@RequestBody MemoryGetRequestDTO dto){
        return new ResponseEntity<>(memoryService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getMemoryType(),dto.getCapacityGB(),dto.getSpeedMHz(),dto.getFormFactor(),dto.getRgbLighting(),dto.getBrand()),
                HttpStatus.OK);
    }

    @GetMapping("/category/memorys/filters")
    public ResponseEntity<MemoryFilterOptionsDTO> getMemoryFilters(){
        return new ResponseEntity<>(memoryService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/memorys/add")
    public ResponseEntity<MemoryResponseDTO> add(@RequestBody MemoryRequestDTO dto){
        return new ResponseEntity<>(memoryService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/memorys/update/{id}")
    public ResponseEntity<MemoryResponseDTO> update(@PathVariable UUID id, @RequestBody MemoryRequestDTO dto){
        return new ResponseEntity<>(memoryService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/memorys/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        memoryService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
