package com.tisitha.product_service.dto.memory;

import lombok.Data;

import java.util.UUID;

@Data
public class MemoryRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String memoryType;
    private String capacityGB;
    private String speedMHz;
    private String formFactor;
    private String rgbLighting;
    private String brand;

    private Integer quantity;
}
