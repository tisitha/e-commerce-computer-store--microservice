package com.tisitha.product_service.dto.monitor;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MonitorRequestDTO {

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
    private String displayResolution;

    @NotBlank
    @Size(max = 50)
    private String refreshRateHz;

    @NotBlank
    @Size(max = 50)
    private String responseTimeMs;

    @NotBlank
    @Size(max = 50)
    private String panelType;

    @NotBlank
    @Size(max = 50)
    private String aspectRatio;

    @NotBlank
    @Size(max = 50)
    private String adaptiveSyncTechnology;

    @NotNull
    @Min(0)
    private Integer quantity;

}
