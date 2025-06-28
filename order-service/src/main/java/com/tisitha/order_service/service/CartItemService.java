package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.CartItemResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CartItemService {

    List<CartItemResponseDTO> getCartItems(String authHeader);

    CartItemResponseDTO addCart(String userId,CartItemRequestDTO dto);

    void deleteCartItem(String userId,UUID id);

    void updateCartItem(String userId,UUID id, Integer quantity);
}
