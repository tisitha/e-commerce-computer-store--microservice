package com.tisitha.product_service.dto.laptop;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LaptopRequestDTO {

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
    private String processorBrand;

    @NotBlank
    @Size(max = 50)
    private String processorSeries;

    @NotBlank
    @Size(max = 50)
    private String ramCapacity;

    @NotBlank
    @Size(max = 50)
    private String storageCapacity;

    @NotBlank
    @Size(max = 50)
    private String displayResolution;

    @NotBlank
    @Size(max = 50)
    private String operatingSystem;

    @NotBlank
    @Size(max = 50)
    private String graphicsCardType;

    @NotBlank
    @Size(max = 50)
    private String featuresIncluded;

    @NotNull
    @Min(0)
    private Integer quantity;

}
