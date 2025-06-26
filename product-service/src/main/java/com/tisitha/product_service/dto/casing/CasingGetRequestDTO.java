package com.tisitha.product_service.dto.casing;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CasingGetRequestDTO {

    @NotNull(message = "pageNumber is required")
    @PositiveOrZero(message = "pageNumber must be zero or a positive number")
    private Integer pageNumber;

    @NotNull(message = "pageSize is required")
    @Min(value = 1, message = "pageSize must be at least 1")
    private Integer pageSize;

    @Size(max = 50, message = "sortBy cannot exceed 50 characters")
    private String sortBy;

    @Pattern(regexp = "asc|desc",flags = Pattern.Flag.CASE_INSENSITIVE,message = "dir must be either 'asc' or 'desc'")
    private String dir;

    private List<@Size(max = 50, message = "brand name cannot exceed 50 chars") String> brand;

    private List<@Size(max = 50, message = "caseType cannot exceed 50 chars") String> caseType;

    private List<@Size(max = 50, message = "maxGPULength cannot exceed 50 chars") String> maxGPULength;

    private List<@Size(max = 50, message = "includedFans cannot exceed 50 chars") String> includedFans;

}
