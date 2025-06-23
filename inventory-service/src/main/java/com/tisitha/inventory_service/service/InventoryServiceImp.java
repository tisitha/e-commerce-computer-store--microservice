package com.tisitha.inventory_service.service;

import com.tisitha.inventory_service.dto.InventoryDTO;
import com.tisitha.inventory_service.exception.DataNotFoundException;
import com.tisitha.inventory_service.model.Inventory;
import com.tisitha.inventory_service.payload.MailBody;
import com.tisitha.inventory_service.producer.KafkaJsonProducer;
import com.tisitha.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryServiceImp implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final KafkaJsonProducer kafkaJsonProducer;

    public InventoryServiceImp(InventoryRepository inventoryRepository, KafkaJsonProducer kafkaJsonProducer) {
        this.inventoryRepository = inventoryRepository;
        this.kafkaJsonProducer = kafkaJsonProducer;
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
        inventory.setQuantity(quantity);
        Inventory newInventory = inventoryRepository.save(inventory);
        if(newInventory.getQuantity()<=2){
            kafkaJsonProducer.sendJson(MailBody.builder()
                    .subject("Low Stock Alert")
                    .text(productName+" ("+newInventory.getProductId()+") is low on stock ("+newInventory.getQuantity()+")")
                    .build());
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
