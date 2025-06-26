package com.tisitha.product_service.dto.motherBoard;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MotherBoardRequestDTO {

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
    private String cpuSocket;

    @NotBlank
    @Size(max = 50)
    private String chipsetSeries;

    @NotBlank
    @Size(max = 50)
    private String formFactor;

    @NotBlank
    @Size(max = 50)
    private String ramType;

    @NotBlank
    @Size(max = 50)
    private String pcieSlotVersion;

    @NotBlank
    @Size(max = 50)
    private String m2Slots;

    @NotBlank
    @Size(max = 50)
    private String wirelessConnectivity;

    @NotNull
    @Min(0)
    private Integer quantity;

}
