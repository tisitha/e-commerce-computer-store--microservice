package com.tisitha.product_service.dto.desktop;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DesktopRequestDTO {

    @NotBlank
    @Size(max = 250) private String name;

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
    private String productType;

    @NotBlank
    @Size(max = 50)
    private String processorBrand;

    @NotBlank
    @Size(max = 50)
    private String processorSeries;

    @NotBlank
    @Size(max = 50)
    private String gpuManufacturer;

    @NotBlank
    @Size(max = 50)
    private String gpuSeries;

    @NotBlank
    @Size(max = 50)
    private String ramCapacity;

    @NotBlank
    @Size(max = 50)
    private String storageType;

    @NotBlank
    @Size(max = 50)
    private String storageCapacity;

    @NotBlank
    @Size(max = 50)
    private String operatingSystem;

    @NotNull
    @Min(0)
    private Integer quantity;

}
