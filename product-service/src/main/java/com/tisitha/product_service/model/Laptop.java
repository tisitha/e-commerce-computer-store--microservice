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
public class Laptop {

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
    private String processorBrand;

    @Column(nullable = false)
    private String processorSeries;

    @Column(nullable = false)
    private String ramCapacity;

    @Column(nullable = false)
    private String storageCapacity;

    @Column(nullable = false)
    private String displayResolution;

    @Column(nullable = false)
    private String operatingSystem;

    @Column(nullable = false)
    private String graphicsCardType;

    @Column(nullable = false)
    private String featuresIncluded;
}
