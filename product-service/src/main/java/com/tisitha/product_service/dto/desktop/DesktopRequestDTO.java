package com.tisitha.product_service.dto.desktop;

import lombok.Data;

@Data
public class DesktopRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
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

    private Integer quantity;
}
