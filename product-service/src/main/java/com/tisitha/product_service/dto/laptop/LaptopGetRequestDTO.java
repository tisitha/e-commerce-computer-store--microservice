package com.tisitha.product_service.dto.laptop;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class LaptopGetRequestDTO {

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

    private List<@Size(max = 50) String> processorBrand;

    private List<@Size(max = 50) String> processorSeries;

    private List<@Size(max = 50) String> ramCapacity;

    private List<@Size(max = 50) String> storageCapacity;

    private List<@Size(max = 50) String> displayResolution;

    private List<@Size(max = 50) String> operatingSystem;

    private List<@Size(max = 50) String> graphicsCardType;

    private List<@Size(max = 50) String> featuresIncluded;

}
