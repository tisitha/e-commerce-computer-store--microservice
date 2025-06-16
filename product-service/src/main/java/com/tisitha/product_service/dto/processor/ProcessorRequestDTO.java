package com.tisitha.product_service.dto.processor;

import lombok.Data;

@Data
public class ProcessorRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
    private double deal;

    private String brand;
    private String cpuSeries;
    private String cpuSocket;
    private String coreCount;
    private String threadCount;
    private String baseClockSpeedGHz;
    private String integratedGraphics;

    private Integer quantity;
}
