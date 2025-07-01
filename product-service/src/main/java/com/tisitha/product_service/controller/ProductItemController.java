package com.tisitha.product_service.controller;

import com.tisitha.product_service.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductItemController {

    private final ProductItemService productItemService;

    public ProductItemController(ProductItemService productItemService) {
        this.productItemService = productItemService;
    }

    @Operation(summary = "Get newly added products")
    @GetMapping("/get-new/{size}")
    public ResponseEntity<List<Object>> getNew(@PathVariable Integer size){
        return new ResponseEntity<>(productItemService.getNew(size), HttpStatus.OK);
    }

    @Operation(summary = "Get top picking products")
    @GetMapping("/get-top-picks/{size}")
    public ResponseEntity<List<Object>> getTop(@PathVariable Integer size){
        return new ResponseEntity<>(productItemService.getTop(size), HttpStatus.OK);
    }

    @Operation(summary = "Get discount products")
    @GetMapping("/get-deals/{size}")
    public ResponseEntity<List<Object>> getDeals(@PathVariable Integer size){
        return new ResponseEntity<>(productItemService.getDeals(size), HttpStatus.OK);
    }

    @Operation(summary = "Get products by search")
    @GetMapping("/search/{text}/{size}")
    public ResponseEntity<List<Object>> search(@PathVariable String text,@PathVariable Integer size){
        return new ResponseEntity<>(productItemService.search(text, size), HttpStatus.OK);
    }
}
