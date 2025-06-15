package com.tisitha.inventory_service.service;

import com.tisitha.inventory_service.dto.InventoryDTO;

import java.util.Optional;
import java.util.UUID;

public interface InventoryService {

   InventoryDTO getQuantity(UUID productId);

    InventoryDTO addQuantity(UUID productId,Integer quantity);

    InventoryDTO updateQuantity(UUID productId,Integer quantity);

    void deleteFromInventory(UUID productId);
}
