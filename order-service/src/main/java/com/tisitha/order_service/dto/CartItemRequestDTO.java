package com.tisitha.order_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemRequestDTO {
    private UUID productId;
    private String title;
    private String imgUrl;
    private double price;
    private double deal;
    private int quantity;
    private UUID customerId;
}
