package com.tisitha.product_service.dto.peripheral;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class PeripheralGetRequestDTO {

    @NotNull
    @PositiveOrZero
    private Integer pageNumber;

    @NotNull
    @Min(1)
    private Integer pageSize;

    @Size(max = 50)
    private String sortBy;

    @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String dir;

    private List<@Size(max = 50) String> brand;

    private List<@Size(max = 50) String> peripheralType;

    private List<@Size(max = 50) String> connectivityType;

    private List<@Size(max = 50) String> rgbLighting;

}