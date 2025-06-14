package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.graphicCard.GraphicCardFilterOptionsDTO;
import com.tisitha.product_service.dto.graphicCard.GraphicCardGetRequestDTO;
import com.tisitha.product_service.dto.graphicCard.GraphicCardRequestDTO;
import com.tisitha.product_service.dto.graphicCard.GraphicCardResponseDTO;
import com.tisitha.product_service.service.GraphicCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class GraphicCardController {

    private final GraphicCardService graphicCardService;

    public GraphicCardController(GraphicCardService graphicCardService) {
        this.graphicCardService = graphicCardService;
    }

    @GetMapping("/category/graphicCards/get")
    public ResponseEntity<ProductPageSortDto<GraphicCardResponseDTO>> getGraphicCard(@RequestBody GraphicCardGetRequestDTO dto){
        return new ResponseEntity<>(graphicCardService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getGpuManufacturer(),dto.getGpuSeries(),dto.getVRamCapacity()),
                HttpStatus.OK);
    }

    @GetMapping("/category/graphicCards/filters")
    public ResponseEntity<GraphicCardFilterOptionsDTO> getGraphicCardFilters(){
        return new ResponseEntity<>(graphicCardService.getAvailableFilters(),
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/graphicCards/add")
    public ResponseEntity<GraphicCardResponseDTO> add(@RequestBody GraphicCardRequestDTO dto){
        return new ResponseEntity<>(graphicCardService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/graphicCards/update/{id}")
    public ResponseEntity<GraphicCardResponseDTO> update(@PathVariable UUID id, @RequestBody GraphicCardRequestDTO dto){
        return new ResponseEntity<>(graphicCardService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/graphicCards/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        graphicCardService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
