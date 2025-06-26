package com.tisitha.product_service.dto.cooling;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CoolingRequestDTO {

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
    private String coolingType;

    @NotBlank
    @Size(max = 50)
    private String socketCompatibility;

    @NotBlank
    @Size(max = 50)
    private String fanSize;

    @NotBlank
    @Size(max = 50)
    private String rgbLighting;

    @NotNull
    @Min(0)
    private Integer quantity;
}
