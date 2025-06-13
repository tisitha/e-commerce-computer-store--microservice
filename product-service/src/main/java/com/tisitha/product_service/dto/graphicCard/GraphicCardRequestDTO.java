package com.tisitha.product_service.dto.graphicCard;

import lombok.Data;

@Data
public class GraphicCardRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String gpuManufacturer;
    private String gpuSeries;
    private String vRamCapacity;

    private Integer quantity;
}
