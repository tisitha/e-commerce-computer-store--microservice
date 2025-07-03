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
public class Casing extends Product{

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String caseType;

    @Column(nullable = false)
    private String maxGPULength;

    @Column(nullable = false)
    private String includedFans;
}
