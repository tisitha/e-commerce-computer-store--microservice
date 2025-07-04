package com.tisitha.order_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemResponseDTO {
    private UUID id;
    private UUID productId;
    private String title;
    private String imgUrl;
    private double price;
    private double deal;
    private String category;
    private int quantity;
    private UUID customerId;
}
