package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.processor.ProcessorFilterOptionsDTO;
import com.tisitha.product_service.dto.processor.ProcessorGetRequestDTO;
import com.tisitha.product_service.dto.processor.ProcessorRequestDTO;
import com.tisitha.product_service.dto.processor.ProcessorResponseDTO;
import com.tisitha.product_service.service.ProcessorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class ProcessorController {

    private final ProcessorService processorService;

    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @Operation(summary = "Get product data category=processors")
    @GetMapping("/product/category/processors/get")
    public ResponseEntity<ProductPageSortDto<ProcessorResponseDTO>> getProcessor(@RequestBody ProcessorGetRequestDTO dto){
        return new ResponseEntity<>(processorService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getBrand(),dto.getCpuSeries(),dto.getCpuSocket(),dto.getCoreCount(),dto.getThreadCount(),dto.getBaseClockSpeedGHz(),dto.getIntegratedGraphics()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for processors")
    @GetMapping("/product/category/processors/filters")
    public ResponseEntity<ProcessorFilterOptionsDTO> getProcessorFilters(){
        return new ResponseEntity<>(processorService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=processors")
    @PostMapping("/admin/product/category/processors/add")
    public ResponseEntity<ProcessorResponseDTO> add(@RequestBody ProcessorRequestDTO dto){
        return new ResponseEntity<>(processorService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=processors")
    @PutMapping("/admin/product/category/processors/update/{id}")
    public ResponseEntity<ProcessorResponseDTO> update(@PathVariable UUID id, @RequestBody ProcessorRequestDTO dto){
        return new ResponseEntity<>(processorService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=processors")
    @DeleteMapping("/admin/product/category/processors/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        processorService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
