package com.tisitha.order_service.service;


import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;

import java.util.UUID;

public interface OrderService {

    OrderResponseDTO getOrderData(OrderGetRequestDTO requestDTO);

    OrderResponseDTO getOrdersByCustomer(String userId,OrderGetRequestDTO requestDTO);

    void addOrder(String userId);

    void deleteOrderData(UUID id);
}
