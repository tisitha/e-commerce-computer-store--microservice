package com.tisitha.product_service.dto.software;

import lombok.Data;

@Data
public class SoftwareRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String brand;
    private String years;
    private String uses;

    private Integer quantity;
}
