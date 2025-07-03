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
public class Memory extends Product{

    @Column(nullable = false)
    private String memoryType;

    @Column(nullable = false)
    private String capacityGB;

    @Column(nullable = false)
    private String speedMHz;

    @Column(nullable = false)
    private String formFactor;

    @Column(nullable = false)
    private String rgbLighting;

    @Column(nullable = false)
    private String brand;
}
