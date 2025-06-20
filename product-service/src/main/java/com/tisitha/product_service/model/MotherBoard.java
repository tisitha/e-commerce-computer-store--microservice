package com.tisitha.product_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotherBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
    private double deal;

    private String brand;
    private String cpuSocket;
    private String chipsetSeries;
    private String formFactor;
    private String ramType;
    private String pcieSlotVersion;
    private String m2Slots;
    private String wirelessConnectivity;
}
