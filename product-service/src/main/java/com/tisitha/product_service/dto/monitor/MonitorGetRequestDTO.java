package com.tisitha.product_service.dto.monitor;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class MonitorGetRequestDTO {

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

    private List<@Size(max = 50) String> displayResolution;

    private List<@Size(max = 50) String> refreshRateHz;

    private List<@Size(max = 50) String> responseTimeMs;

    private List<@Size(max = 50) String> panelType;

    private List<@Size(max = 50) String> aspectRatio;

    private List<@Size(max = 50) String> adaptiveSyncTechnology;

}
