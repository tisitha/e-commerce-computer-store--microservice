package com.tisitha.inventory_service.service;

import com.tisitha.inventory_service.dto.InventoryDTO;
import com.tisitha.inventory_service.model.Inventory;
import com.tisitha.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryServiceImp implements InventoryService{

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImp(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryDTO getQuantity(UUID productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(()->new RuntimeException("Not in inventory"));
        return new InventoryDTO(inventory.getId(), inventory.getProductId(), inventory.getQuantity());
    }

    @Override
    public InventoryDTO addQuantity(UUID productId, Integer quantity) {
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setQuantity(quantity);
        Inventory newInventory = inventoryRepository.save(inventory);
        return new InventoryDTO(newInventory.getId(),newInventory.getProductId(),newInventory.getQuantity());
    }

    @Override
    public InventoryDTO updateQuantity(UUID productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(()->new RuntimeException("Not in inventory"));
        inventory.setQuantity(quantity);
        Inventory newInventory = inventoryRepository.save(inventory);
        return new InventoryDTO(newInventory.getId(),newInventory.getProductId(),newInventory.getQuantity());
    }

    @Override
    public void deleteFromInventory(UUID productId) {
        if(!inventoryRepository.existsByProductId(productId)){
            throw new RuntimeException("not exist by pid");
        }
        inventoryRepository.deleteByProductId(productId);
    }
}
