package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.motherBoard.MotherBoardFilterOptionsDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardGetRequestDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardRequestDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardResponseDTO;
import com.tisitha.product_service.service.MotherBoardService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class MotherBoardController {

    private final MotherBoardService motherBoardService;

    public MotherBoardController(MotherBoardService motherBoardService) {
        this.motherBoardService = motherBoardService;
    }

    @Operation(summary = "Get a product category=mother-boards")
    @GetMapping("/product/category/mother-boards/item/{id}")
    public ResponseEntity<MotherBoardResponseDTO> getProduct(@PathVariable UUID id){
        return new ResponseEntity<>(motherBoardService.getProduct(id),HttpStatus.OK);
    }

    @Operation(summary = "Get products of category=mother-boards")
    @GetMapping("/product/category/mother-boards/get")
    public ResponseEntity<ProductPageSortDto<MotherBoardResponseDTO>> getMotherBoard(@Valid @RequestBody MotherBoardGetRequestDTO dto){
        return new ResponseEntity<>(motherBoardService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getCpuSocket(),dto.getChipsetSeries(),dto.getFormFactor(),dto.getRamType(),dto.getPcieSlotVersion(),dto.getM2Slots(),dto.getWirelessConnectivity()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for mother-boards")
    @GetMapping("/product/category/mother-boards/filters")
    public ResponseEntity<MotherBoardFilterOptionsDTO> getMotherBoardFilters(){
        return new ResponseEntity<>(motherBoardService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=mother-boards")
    @PostMapping("/admin/product/category/mother-boards/add")
    public ResponseEntity<MotherBoardResponseDTO> add(@Valid @RequestBody MotherBoardRequestDTO dto){
        return new ResponseEntity<>(motherBoardService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=mother-boards")
    @PutMapping("/admin/product/category/mother-boards/update/{id}")
    public ResponseEntity<MotherBoardResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody MotherBoardRequestDTO dto){
        return new ResponseEntity<>(motherBoardService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=mother-boards")
    @DeleteMapping("/admin/product/category/mother-boards/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        motherBoardService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
