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
public class MotherBoard extends Product{

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String cpuSocket;

    @Column(nullable = false)
    private String chipsetSeries;

    @Column(nullable = false)
    private String formFactor;

    @Column(nullable = false)
    private String ramType;

    @Column(nullable = false)
    private String pcieSlotVersion;

    @Column(nullable = false)
    private String m2Slots;

    @Column(nullable = false)
    private String wirelessConnectivity;
}
