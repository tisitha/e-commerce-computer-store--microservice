package com.tisitha.product_service.dto.graphicsCard;

import lombok.Data;

@Data
public class GraphicsCardRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
    private double deal;

    private String gpuManufacturer;
    private String gpuSeries;
    private String vramGb;

    private Integer quantity;
}
