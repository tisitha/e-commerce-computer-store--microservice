package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardFilterOptionsDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardGetRequestDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardRequestDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardResponseDTO;
import com.tisitha.product_service.service.GraphicsCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class GraphicsCardController {

    private final GraphicsCardService graphicsCardService;

    public GraphicsCardController(GraphicsCardService graphicsCardService) {
        this.graphicsCardService = graphicsCardService;
    }

    @GetMapping("/category/graphics-cards/get")
    public ResponseEntity<ProductPageSortDto<GraphicsCardResponseDTO>> getGraphicCard(@RequestBody GraphicsCardGetRequestDTO dto){
        return new ResponseEntity<>(graphicsCardService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getGpuManufacturer(),dto.getGpuSeries(),dto.getVramGb()),
                HttpStatus.OK);
    }

    @GetMapping("/category/graphics-cards/filters")
    public ResponseEntity<GraphicsCardFilterOptionsDTO> getGraphicCardFilters(){
        return new ResponseEntity<>(graphicsCardService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/graphics-cards/add")
    public ResponseEntity<GraphicsCardResponseDTO> add(@RequestBody GraphicsCardRequestDTO dto){
        return new ResponseEntity<>(graphicsCardService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/graphic-cards/update/{id}")
    public ResponseEntity<GraphicsCardResponseDTO> update(@PathVariable UUID id, @RequestBody GraphicsCardRequestDTO dto){
        return new ResponseEntity<>(graphicsCardService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/graphic-cards/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        graphicsCardService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
