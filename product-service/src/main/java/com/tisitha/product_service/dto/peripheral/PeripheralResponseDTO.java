package com.tisitha.product_service.dto.peripheral;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PeripheralResponseDTO {

    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
    private double deal;

    private String brand;
    private String peripheralType;
    private String connectivityType;
    private String rgbLighting;
}
