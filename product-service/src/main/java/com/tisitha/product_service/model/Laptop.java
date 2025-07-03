package com.tisitha.product_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Laptop extends Product{

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
