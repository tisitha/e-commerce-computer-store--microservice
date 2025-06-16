package com.tisitha.product_service.feign;

import com.tisitha.product_service.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @GetMapping("inventory/get-quantity/{id}")
    public ResponseEntity<InventoryDTO> getQuantity(@PathVariable UUID id);

    @PostMapping("inventory/add-quantity/{id}")
    public ResponseEntity<InventoryDTO> addQuantity(@PathVariable UUID id,@RequestParam Integer quantity);

    @PutMapping("inventory/update-quantity/{id}")
    public ResponseEntity<InventoryDTO> updateQuantity(@PathVariable UUID id,@RequestParam Integer quantity);

    @DeleteMapping("inventory/delete-quantity/{id}")
    public ResponseEntity<Void> deleteQuantity(@PathVariable UUID id);

}
