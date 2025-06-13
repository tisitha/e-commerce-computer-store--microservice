package com.tisitha.product_service.dto.desktop;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DesktopResponseDTO {

    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String brand;
    private String productType;
    private String processorBrand;
    private String processorSeries;
    private String gpuManufacturer;
    private String gpuSeries;
    private String ramCapacity;
    private String storageType;
    private String storageCapacity;
    private String operatingSystem;
}
