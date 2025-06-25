package com.tisitha.order_service.service;


import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDTO getOrderData(OrderGetRequestDTO requestDTO);

    OrderResponseDTO getOrdersByCustomer(UUID id,OrderGetRequestDTO requestDTO);

    void addOrder(String authHeader,UUID cid);

    void deleteOrderData(UUID id);
}
