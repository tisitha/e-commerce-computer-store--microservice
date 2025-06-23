package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.motherBoard.MotherBoardFilterOptionsDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardGetRequestDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardRequestDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardResponseDTO;
import com.tisitha.product_service.service.MotherBoardService;
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

    @GetMapping("/product/category/mother-boards/get")
    public ResponseEntity<ProductPageSortDto<MotherBoardResponseDTO>> getMotherBoard(@RequestBody MotherBoardGetRequestDTO dto){
        return new ResponseEntity<>(motherBoardService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getCpuSocket(),dto.getChipsetSeries(),dto.getFormFactor(),dto.getRamType(),dto.getPcieSlotVersion(),dto.getM2Slots(),dto.getWirelessConnectivity()),
                HttpStatus.OK);
    }

    @GetMapping("/product/category/mother-boards/filters")
    public ResponseEntity<MotherBoardFilterOptionsDTO> getMotherBoardFilters(){
        return new ResponseEntity<>(motherBoardService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/mother-boards/add")
    public ResponseEntity<MotherBoardResponseDTO> add(@RequestBody MotherBoardRequestDTO dto){
        return new ResponseEntity<>(motherBoardService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/mother-boards/update/{id}")
    public ResponseEntity<MotherBoardResponseDTO> update(@PathVariable UUID id, @RequestBody MotherBoardRequestDTO dto){
        return new ResponseEntity<>(motherBoardService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/mother-boards/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        motherBoardService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
