package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.peripheral.PeripheralFilterOptionsDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralGetRequestDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralRequestDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralResponseDTO;
import com.tisitha.product_service.service.PeripheralService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class PeripheralController {

    private final PeripheralService peripheralService;

    public PeripheralController(PeripheralService peripheralService) {
        this.peripheralService = peripheralService;
    }

    @Operation(summary = "Get a product category=peripherals")
    @GetMapping("/product/category/peripherals/item/{id}")
    public ResponseEntity<PeripheralResponseDTO> getProduct(@PathVariable UUID id){
        return new ResponseEntity<>(peripheralService.getProduct(id),HttpStatus.OK);
    }

    @Operation(summary = "Get products of category=peripherals")
    @GetMapping("/product/category/peripherals/get")
    public ResponseEntity<ProductPageSortDto<PeripheralResponseDTO>> getPeripheral(@Valid @RequestBody PeripheralGetRequestDTO dto){
        return new ResponseEntity<>(peripheralService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getPeripheralType(),dto.getConnectivityType(),dto.getRgbLighting()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for peripherals")
    @GetMapping("/product/category/peripherals/filters")
    public ResponseEntity<PeripheralFilterOptionsDTO> getPeripheralFilters(){
        return new ResponseEntity<>(peripheralService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=peripherals")
    @PostMapping("/admin/product/category/peripherals/add")
    public ResponseEntity<PeripheralResponseDTO> add(@Valid @RequestBody PeripheralRequestDTO dto){
        return new ResponseEntity<>(peripheralService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=peripherals")
    @PutMapping("/admin/product/category/peripherals/update/{id}")
    public ResponseEntity<PeripheralResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody PeripheralRequestDTO dto){
        return new ResponseEntity<>(peripheralService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=peripherals")
    @DeleteMapping("/admin/product/category/peripherals/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        peripheralService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
