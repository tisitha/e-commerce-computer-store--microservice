package com.tisitha.inventory_service.controller;

import com.tisitha.inventory_service.dto.InventoryDTO;
import com.tisitha.inventory_service.service.InventoryService;
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

    @GetMapping("get-quantity/{id}")
    public ResponseEntity<InventoryDTO> getQuantity(@PathVariable UUID id){
        return new ResponseEntity<>(inventoryService.getQuantity(id), HttpStatus.OK);
    }

    @PostMapping("add-quantity/{id}")
    public ResponseEntity<InventoryDTO> addQuantity(@PathVariable UUID id,@RequestParam Integer quantity){
        return new ResponseEntity<>(inventoryService.addQuantity(id,quantity), HttpStatus.CREATED);
    }

    @PutMapping("update-quantity/{id}")
    public ResponseEntity<InventoryDTO> updateQuantity(@PathVariable UUID id,@RequestParam Integer quantity){
        return new ResponseEntity<>(inventoryService.updateQuantity(id,quantity), HttpStatus.CREATED);
    }

    @DeleteMapping("delete-quantity/{id}")
    public ResponseEntity<Void> deleteQuantity(@PathVariable UUID id){
        inventoryService.deleteFromInventory(id);
        return ResponseEntity.ok().build();
    }
}
