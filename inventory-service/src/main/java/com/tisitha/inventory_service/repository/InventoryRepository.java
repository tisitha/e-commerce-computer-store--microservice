package com.tisitha.inventory_service.repository;

import com.tisitha.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    Optional<Inventory> findByProductId(UUID id);


    void deleteByProductId(UUID productId);

    boolean existsByProductId(UUID productId);
}
