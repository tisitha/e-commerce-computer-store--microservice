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
public class Storage extends Product{

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String storageType;

    @Column(nullable = false)
    private String capacityGB;

    @Column(nullable = false)
    private String interfaceType;

    @Column(nullable = false)
    private String usageType;
}
