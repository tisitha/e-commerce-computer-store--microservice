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
public class Monitor extends Product{

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String displayResolution;

    @Column(nullable = false)
    private String refreshRateHz;

    @Column(nullable = false)
    private String responseTimeMs;

    @Column(nullable = false)
    private String panelType;

    @Column(nullable = false)
    private String aspectRatio;

    @Column(nullable = false)
    private String adaptiveSyncTechnology;
}
