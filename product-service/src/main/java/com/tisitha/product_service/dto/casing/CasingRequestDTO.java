package com.tisitha.product_service.dto.casing;

import lombok.Data;

@Data
public class CasingRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String brand;
    private String caseType;
    private String maxGPULength;
    private String includedFans;

    private Integer quantity;
}
