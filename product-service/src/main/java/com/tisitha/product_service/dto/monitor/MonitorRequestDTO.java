package com.tisitha.product_service.dto.monitor;

import lombok.Data;

@Data
public class MonitorRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String brand;
    private String displayResolution;
    private String refreshRateHz;
    private String responseTimeMs;
    private String panelType;
    private String aspectRatio;
    private String adaptiveSyncTechnology;

    private Integer quantity;
}
