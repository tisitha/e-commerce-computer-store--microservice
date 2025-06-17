package com.tisitha.order_service.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

    private UUID productId;
    private String title;
    private double price;
    private double deal;
    private int quantity;

}
