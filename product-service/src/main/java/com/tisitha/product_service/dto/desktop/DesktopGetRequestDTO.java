package com.tisitha.product_service.dto.desktop;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class DesktopGetRequestDTO {

    @NotNull(message = "pageNumber is required")
    @PositiveOrZero(message = "pageNumber must be zero or a positive number")
    private Integer pageNumber;

    @NotNull(message = "pageSize is required")
    @Min(value = 1, message = "pageSize must be at least 1")
    private Integer pageSize;

    @Size(max = 50, message = "sortBy cannot exceed 50 chars")
    private String sortBy;

    @Pattern(regexp = "asc|desc",flags = Pattern.Flag.CASE_INSENSITIVE,message = "dir must be either 'asc' or 'desc'")
    private String dir;

    private List<@Size(max = 50, message = "brand cannot exceed 50 chars") String> brand;

    private List<@Size(max = 50, message = "productType cannot exceed 50 chars") String> productType;

    private List<@Size(max = 50, message = "processorBrand cannot exceed 50 chars") String> processorBrand;

    private List<@Size(max = 50, message = "processorSeries cannot exceed 50 chars") String> processorSeries;

    private List<@Size(max = 50, message = "gpuManufacturer cannot exceed 50 chars") String> gpuManufacturer;

    private List<@Size(max = 50, message = "gpuSeries cannot exceed 50 chars") String> gpuSeries;

    private List<@Size(max = 50, message = "ramCapacity cannot exceed 50 chars") String> ramCapacity;

    private List<@Size(max = 50, message = "storageType cannot exceed 50 chars") String> storageType;

    private List<@Size(max = 50, message = "storageCapacity cannot exceed 50 chars") String> storageCapacity;

    private List<@Size(max = 50, message = "operatingSystem cannot exceed 50 chars") String> operatingSystem;

}
