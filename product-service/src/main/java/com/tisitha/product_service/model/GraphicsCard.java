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
public class GraphicsCard extends Product{

    @Column(nullable = false)
    private String gpuManufacturer;

    @Column(nullable = false)
    private String gpuSeries;

    @Column(nullable = false)
    private String vramGb;

}
