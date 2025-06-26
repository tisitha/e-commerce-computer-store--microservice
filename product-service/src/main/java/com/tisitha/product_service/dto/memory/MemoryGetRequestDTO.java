package com.tisitha.product_service.dto.memory;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class MemoryGetRequestDTO {

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

    private List<@Size(max = 50) String> memoryType;

    private List<@Size(max = 50) String> capacityGB;

    private List<@Size(max = 50) String> speedMHz;

    private List<@Size(max = 50) String> formFactor;

    private List<@Size(max = 50) String> rgbLighting;

    private List<@Size(max = 50) String> brand;

}
