package com.tisitha.product_service.dto.powerSupply;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PowerSupplyRequestDTO {

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
    private String wattageOutput;

    @NotBlank
    @Size(max = 50)
    private String certificationRating;

    @NotBlank
    @Size(max = 50)
    private String formFactor;

    @NotBlank
    @Size(max = 50)
    private String modularityType;

    @NotNull
    @Min(0)
    private Integer quantity;

}