package com.tisitha.order_service.repository;

import com.tisitha.order_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    List<CartItem> findAllByCustomerId(UUID customerId);

    CartItem findByProductIdAndCustomerId(UUID productId, UUID customerId);
}
