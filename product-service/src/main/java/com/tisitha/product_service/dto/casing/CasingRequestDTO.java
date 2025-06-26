package com.tisitha.product_service.dto.casing;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CasingRequestDTO {

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
    private String caseType;

    @NotBlank
    @Size(max = 50)
    private String maxGPULength;

    @NotBlank
    @Size(max = 50)
    private String includedFans;

    @NotNull
    @Min(0)
    private Integer quantity;
}
