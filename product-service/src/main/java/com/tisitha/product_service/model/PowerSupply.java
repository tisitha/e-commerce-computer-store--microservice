package com.tisitha.product_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PowerSupply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean latest;

    @Column(nullable = false)
    private boolean top;

    @Column(nullable = false)
    private double deal;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String wattageOutput;

    @Column(nullable = false)
    private String certificationRating;

    @Column(nullable = false)
    private String formFactor;

    @Column(nullable = false)
    private String modularityType;
}
