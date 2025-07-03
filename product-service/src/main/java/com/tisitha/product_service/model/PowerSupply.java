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
public class PowerSupply extends Product{

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
