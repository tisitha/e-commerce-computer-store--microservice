package com.tisitha.product_service.dto.cooling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CoolingResponseDTO {

    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String brand;
    private String coolingType;
    private String socketCompatibility;
    private String fanSize;
    private String rgbLighting;
}
