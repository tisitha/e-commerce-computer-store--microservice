package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardFilterOptionsDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardGetRequestDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardRequestDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardResponseDTO;
import com.tisitha.product_service.service.GraphicsCardService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class GraphicsCardController {

    private final GraphicsCardService graphicsCardService;

    public GraphicsCardController(GraphicsCardService graphicsCardService) {
        this.graphicsCardService = graphicsCardService;
    }

    @Operation(summary = "Get product data category=graphics-cards")
    @GetMapping("/product/category/graphics-cards/get")
    public ResponseEntity<ProductPageSortDto<GraphicsCardResponseDTO>> getGraphicCard(@Valid @RequestBody GraphicsCardGetRequestDTO dto){
        return new ResponseEntity<>(graphicsCardService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getGpuManufacturer(),dto.getGpuSeries(),dto.getVramGb()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get filter options for graphics-cards")
    @GetMapping("/product/category/graphics-cards/filters")
    public ResponseEntity<GraphicsCardFilterOptionsDTO> getGraphicCardFilters(){
        return new ResponseEntity<>(graphicsCardService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add product data category=graphics-cards")
    @PostMapping("/admin/product/category/graphics-cards/add")
    public ResponseEntity<GraphicsCardResponseDTO> add(@Valid @RequestBody GraphicsCardRequestDTO dto){
        return new ResponseEntity<>(graphicsCardService.add(dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update product data category=graphics-cards")
    @PutMapping("/admin/product/category/graphic-cards/update/{id}")
    public ResponseEntity<GraphicsCardResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody GraphicsCardRequestDTO dto){
        return new ResponseEntity<>(graphicsCardService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product data category=graphics-cards")
    @DeleteMapping("/admin/product/category/graphic-cards/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        graphicsCardService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
