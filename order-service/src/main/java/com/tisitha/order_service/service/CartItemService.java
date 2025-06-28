package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.CartItemResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CartItemService {

    List<CartItemResponseDTO> getCartItems(String authHeader,UUID id);

    CartItemResponseDTO addCart(String authHeader,CartItemRequestDTO dto);

    void deleteCartItem(String authHeader,UUID id);

    void updateCartItem(String authHeader,UUID id, Integer quantity);
}
