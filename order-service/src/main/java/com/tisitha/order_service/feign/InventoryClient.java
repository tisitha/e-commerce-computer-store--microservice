package com.tisitha.order_service.feign;


import com.tisitha.order_service.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @GetMapping("inventory/get-quantity/{id}")
    ResponseEntity<InventoryDTO> getQuantity(@PathVariable UUID id);


    @PutMapping("inventory/update-quantity/{id}")
    ResponseEntity<InventoryDTO> updateQuantity(@PathVariable UUID id, @RequestParam Integer quantity, @RequestParam String productName);

}
