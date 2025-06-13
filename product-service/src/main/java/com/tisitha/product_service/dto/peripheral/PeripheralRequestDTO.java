package com.tisitha.product_service.dto.peripheral;

import lombok.Data;

@Data
public class PeripheralRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String brand;
    private String peripheralType;
    private String connectivityType;
    private String rgbLighting;

    private Integer quantity;
}
