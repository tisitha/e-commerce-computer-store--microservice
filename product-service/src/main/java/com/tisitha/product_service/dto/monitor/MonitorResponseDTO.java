package com.tisitha.product_service.dto.monitor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MonitorResponseDTO {

    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
    private double deal;

    private String brand;
    private String displayResolution;
    private String refreshRateHz;
    private String responseTimeMs;
    private String panelType;
    private String aspectRatio;
    private String adaptiveSyncTechnology;
}
