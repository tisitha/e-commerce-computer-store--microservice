package com.tisitha.product_service.dto.processor;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProcessorRequestDTO {

    @NotBlank
    @Size(max = 250)
    private String name;

    private String imgUrl;

    private String description;

    @DecimalMin(value = "0.00", inclusive = true)
    private double price;

    private boolean latest;

    private boolean top;

    @DecimalMin(value = "0.00", inclusive = true)
    private double deal;

    @NotBlank
    @Size(max = 50)
    private String brand;

    @NotBlank
    @Size(max = 50)
    private String cpuSeries;

    @NotBlank
    @Size(max = 50)
    private String cpuSocket;

    @NotBlank
    @Size(max = 50)
    private String coreCount;

    @NotBlank
    @Size(max = 50)
    private String threadCount;

    @NotBlank
    @Size(max = 50)
    private String baseClockSpeedGHz;

    @NotBlank
    @Size(max = 50)
    private String integratedGraphics;

    @NotNull
    @Min(0)
    private Integer quantity;

}