package com.tisitha.order_service.service;


import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDTO getOrderData(OrderGetRequestDTO requestDTO);

    void addOrder(List<CartItemRequestDTO> dtos);

    void deleteOrderData(UUID id);
}
