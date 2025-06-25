package com.tisitha.order_service.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderItem {

    private UUID productId;
    private String title;
    private double price;
    private double deal;
    private int quantity;

}
