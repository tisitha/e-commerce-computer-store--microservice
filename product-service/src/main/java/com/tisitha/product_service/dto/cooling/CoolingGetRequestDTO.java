package com.tisitha.product_service.dto.cooling;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CoolingGetRequestDTO {

    @NotNull(message = "pageNumber is required")
    @PositiveOrZero(message = "pageNumber must be zero or a positive number")
    private Integer pageNumber;

    @NotNull(message = "pageSize is required")
    @Min(value = 1, message = "pageSize must be at least 1")
    private Integer pageSize;

    @Size(max = 50, message = "sortBy cannot exceed 50 characters")
    private String sortBy;

    @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE, message = "dir must be either 'asc' or 'desc'")
    private String dir;

    private List<@Size(max = 50, message = "brand cannot exceed 50 chars") String> brand;

    private List<@Size(max = 50, message = "coolingType cannot exceed 50 chars") String> coolingType;

    private List<@Size(max = 50, message = "socketCompatibility cannot exceed 50 chars") String> socketCompatibility;

    private List<@Size(max = 50, message = "fanSize cannot exceed 50 chars") String> fanSize;

    private List<@Size(max = 50, message = "rgbLighting cannot exceed 50 chars") String> rgbLighting;

}