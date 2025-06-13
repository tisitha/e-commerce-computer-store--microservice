package com.tisitha.product_service.dto.powerSupply;

import lombok.Data;

@Data
public class PowerSupplyRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String brand;
    private String wattageOutput;
    private String certificationRating;
    private String formFactor;
    private String modularityType;

    private Integer quantity;
}
