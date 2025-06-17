package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.CartItemResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CartItemService {

    List<CartItemResponseDTO> getCartItems(UUID id);

    CartItemResponseDTO addCart(CartItemRequestDTO dto);

    void deleteCartItem(UUID id);

    void updateCartItem(UUID id, Integer quantity);
}
