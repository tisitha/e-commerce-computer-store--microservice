package com.tisitha.product_service.controller;

import com.tisitha.product_service.dto.CasingGetRequestDTO;
import com.tisitha.product_service.dto.CasingRequestDTO;
import com.tisitha.product_service.dto.CasingResponseDTO;
import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.service.CasingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("category/casings")
public class CasingController {

    private final CasingService casingService;

    public CasingController(CasingService casingService) {
        this.casingService = casingService;
    }

    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> caseType;
    private List<String> maxGPULength;
    private List<String> includedFans;

    @GetMapping("/get")
    public ResponseEntity<ProductPageSortDto<CasingResponseDTO>> getCasing(@RequestBody CasingGetRequestDTO dto){
        return new ResponseEntity<>(casingService.getAll(dto.getPageNumber(), dto.getPageSize(),dto.getSortBy(),dto.getDir(),dto.getCaseType(),dto.getMaxGPULength(),dto.getIncludedFans()),
                HttpStatus.OK);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<CasingResponseDTO> add(@RequestBody CasingRequestDTO dto){
        return new ResponseEntity<>(casingService.add(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<CasingResponseDTO> add(@PathVariable UUID id, @RequestBody CasingRequestDTO dto){
        return new ResponseEntity<>(casingService.updateProduct(id,dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/delete/{id}")
    public ResponseEntity<Void> add(@PathVariable UUID id){
        casingService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
