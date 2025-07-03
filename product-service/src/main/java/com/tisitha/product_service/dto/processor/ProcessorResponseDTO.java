package com.tisitha.product_service.dto.processor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProcessorResponseDTO {

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
    private String cpuSeries;
    private String cpuSocket;
    private String coreCount;
    private String threadCount;
    private String baseClockSpeedGHz;
    private String integratedGraphics;

    private Integer quantity;
}
