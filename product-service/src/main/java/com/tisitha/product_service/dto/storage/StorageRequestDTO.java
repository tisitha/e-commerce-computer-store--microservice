package com.tisitha.product_service.dto.storage;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StorageRequestDTO {

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
    private String storageType;

    @NotBlank
    @Size(max = 50)
    private String capacityGB;

    @NotBlank
    @Size(max = 50)
    private String interfaceType;

    @NotBlank
    @Size(max = 50)
    private String usageType;

    @NotNull
    @Min(0)
    private Integer quantity;

}
