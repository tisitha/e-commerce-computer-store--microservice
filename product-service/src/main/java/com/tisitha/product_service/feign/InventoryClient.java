package com.tisitha.product_service.feign;

import com.tisitha.product_service.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "INVENTORY-SERVICE")
@RequestMapping("/inventory")
public interface InventoryClient {

    @GetMapping("/get-quantity")
    public ResponseEntity<InventoryDTO> getQuantity(UUID id);

    @PostMapping("/add-quantity")
    public ResponseEntity<InventoryDTO> addQuantity(UUID id,Integer quantity);

    @PutMapping("/update-quantity")
    public ResponseEntity<InventoryDTO> updateQuantity(UUID id,Integer quantity);

    @DeleteMapping("/delete-quantity")
    public ResponseEntity<Void> deleteQuantity(UUID id);

}
