package com.tisitha.inventory_service.controller;

import com.tisitha.inventory_service.dto.InventoryDTO;
import com.tisitha.inventory_service.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("inventory/")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(summary = "Get quantity of a product")
    @GetMapping("get-quantity/{id}")
    public ResponseEntity<InventoryDTO> getQuantity(@PathVariable UUID id){
        return new ResponseEntity<>(inventoryService.getQuantity(id), HttpStatus.OK);
    }

    @Operation(summary = "Add a quantity data of a product")
    @PostMapping("add-quantity/{id}")
    public ResponseEntity<InventoryDTO> addQuantity(@PathVariable UUID id,@RequestParam Integer quantity){
        return new ResponseEntity<>(inventoryService.addQuantity(id,quantity), HttpStatus.CREATED);
    }

    @Operation(summary = "Update quantity of a product")
    @PutMapping("update-quantity/{id}")
    public ResponseEntity<InventoryDTO> updateQuantity(@PathVariable UUID id,@RequestParam Integer quantity,@RequestParam String productName){
        return new ResponseEntity<>(inventoryService.updateQuantity(id,quantity,productName), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete quantity data of a product")
    @DeleteMapping("delete-quantity/{id}")
    public ResponseEntity<Void> deleteQuantity(@PathVariable UUID id){
        inventoryService.deleteFromInventory(id);
        return ResponseEntity.ok().build();
    }
}
