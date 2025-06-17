package com.tisitha.order_service.dto;

import com.tisitha.order_service.model.Order;

import java.util.List;

public record OrderResponseDTO(List<Order> orderList ,
                                    long totalElement,
                                    int pageCount,
                                    boolean isLast) {
}
