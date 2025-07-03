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
public class Desktop extends Product{

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String productType;

    @Column(nullable = false)
    private String processorBrand;

    @Column(nullable = false)
    private String processorSeries;

    @Column(nullable = false)
    private String gpuManufacturer;

    @Column(nullable = false)
    private String gpuSeries;

    @Column(nullable = false)
    private String ramCapacity;

    @Column(nullable = false)
    private String storageType;

    @Column(nullable = false)
    private String storageCapacity;

    @Column(nullable = false)
    private String operatingSystem;
}
