package com.tisitha.product_service.dto.memory;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MemoryRequestDTO {

    @NotBlank @Size(max = 250)
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
    private String memoryType;

    @NotBlank
    @Size(max = 50)
    private String capacityGB;

    @NotBlank
    @Size(max = 50)
    private String speedMHz;

    @NotBlank
    @Size(max = 50)
    private String formFactor;

    @NotBlank
    @Size(max = 50)
    private String rgbLighting;

    @NotBlank
    @Size(max = 50)
    private String brand;

    @NotNull
    @Min(0)
    private Integer quantity;

}
