package com.tisitha.product_service.dto.software;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SoftwareRequestDTO {

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
    private String years;

    @NotBlank
    @Size(max = 50)
    private String uses;

    @NotNull
    @Min(0)
    private Integer quantity;

}
