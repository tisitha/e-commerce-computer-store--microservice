package com.tisitha.product_service.dto.graphicsCard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GraphicsCardResponseDTO {

    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String gpuManufacturer;
    private String gpuSeries;
    private String vramGb;
}
