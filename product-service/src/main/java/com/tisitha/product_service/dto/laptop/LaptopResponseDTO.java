package com.tisitha.product_service.dto.laptop;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LaptopResponseDTO {

    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
    private double deal;
    private String category;

    private String brand;
    private String processorBrand;
    private String processorSeries;
    private String ramCapacity;
    private String storageCapacity;
    private String displayResolution;
    private String operatingSystem;
    private String graphicsCardType;
    private String featuresIncluded;

    private Integer quantity;
}
