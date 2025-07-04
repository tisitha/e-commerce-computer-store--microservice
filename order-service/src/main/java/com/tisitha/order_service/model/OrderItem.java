package com.tisitha.order_service.model;

import jakarta.persistence.Column;
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

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double deal;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int quantity;

}
