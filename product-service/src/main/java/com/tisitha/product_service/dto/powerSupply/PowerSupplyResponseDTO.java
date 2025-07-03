package com.tisitha.product_service.dto.powerSupply;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PowerSupplyResponseDTO {

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
    private String wattageOutput;
    private String certificationRating;
    private String formFactor;
    private String modularityType;

    private Integer quantity;
}
