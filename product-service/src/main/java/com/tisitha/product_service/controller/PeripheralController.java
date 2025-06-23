package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.peripheral.PeripheralFilterOptionsDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralGetRequestDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralRequestDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralResponseDTO;
import com.tisitha.product_service.service.PeripheralService;
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

    @GetMapping("/product/category/peripherals/get")
    public ResponseEntity<ProductPageSortDto<PeripheralResponseDTO>> getPeripheral(@RequestBody PeripheralGetRequestDTO dto){
        return new ResponseEntity<>(peripheralService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getPeripheralType(),dto.getConnectivityType(),dto.getRgbLighting()),
                HttpStatus.OK);
    }

    @GetMapping("/product/category/peripherals/filters")
    public ResponseEntity<PeripheralFilterOptionsDTO> getPeripheralFilters(){
        return new ResponseEntity<>(peripheralService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/peripherals/add")
    public ResponseEntity<PeripheralResponseDTO> add(@RequestBody PeripheralRequestDTO dto){
        return new ResponseEntity<>(peripheralService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/peripherals/update/{id}")
    public ResponseEntity<PeripheralResponseDTO> update(@PathVariable UUID id, @RequestBody PeripheralRequestDTO dto){
        return new ResponseEntity<>(peripheralService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/peripherals/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        peripheralService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
