package com.tisitha.product_service.dto.processor;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ProcessorGetRequestDTO {

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

    private List<@Size(max = 50) String> cpuSeries;

    private List<@Size(max = 50) String> cpuSocket;

    private List<@Size(max = 50) String> coreCount;

    private List<@Size(max = 50) String> threadCount;

    private List<@Size(max = 50) String> baseClockSpeedGHz;

    private List<@Size(max = 50) String> integratedGraphics;

}
