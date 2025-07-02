package com.tisitha.inventory_service.service;

import com.tisitha.inventory_service.dto.InventoryDTO;
import com.tisitha.inventory_service.exception.DataNotFoundException;
import com.tisitha.inventory_service.model.Inventory;
import com.tisitha.inventory_service.producer.KafkaProducer;
import com.tisitha.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryServiceImp implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final KafkaProducer kafkaProducer;

    public InventoryServiceImp(InventoryRepository inventoryRepository, KafkaProducer kafkaProducer) {
        this.inventoryRepository = inventoryRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public InventoryDTO getQuantity(UUID productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(()->new DataNotFoundException("Data of product id:"+productId+" not found"));
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
    public InventoryDTO updateQuantity(UUID productId, Integer quantity, String productName) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(()->new DataNotFoundException("Data of product id:"+productId+" not found"));
        if(quantity<0){
            quantity=0;
        }
        inventory.setQuantity(quantity);
        Inventory newInventory = inventoryRepository.save(inventory);
        if(newInventory.getQuantity()<=1){
            kafkaProducer.send("Low Stock Alert\nProduct: " + productName + " (ID: " + newInventory.getProductId() + ")\nCurrent Stock: " + newInventory.getQuantity());
        }
        return new InventoryDTO(newInventory.getId(),newInventory.getProductId(),newInventory.getQuantity());
    }

    @Override
    public void deleteFromInventory(UUID productId) {
        if(!inventoryRepository.existsByProductId(productId)){
            throw new DataNotFoundException("Data of product id:"+productId+" not found");
        }
        inventoryRepository.deleteByProductId(productId);
    }
}
