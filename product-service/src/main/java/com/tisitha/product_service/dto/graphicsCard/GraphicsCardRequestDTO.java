package com.tisitha.product_service.dto.graphicsCard;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class GraphicsCardRequestDTO {

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
    private String gpuManufacturer;

    @NotBlank
    @Size(max = 50)
    private String gpuSeries;

    @NotBlank
    @Size(max = 50)
    private String vramGb;

    @NotNull
    @Min(0)
    private Integer quantity;

}