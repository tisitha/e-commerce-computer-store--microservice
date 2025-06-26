package com.tisitha.product_service.dto.powerSupply;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class PowerSupplyGetRequestDTO {

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

    private List<@Size(max = 50) String> wattageOutput;

    private List<@Size(max = 50) String> certificationRating;

    private List<@Size(max = 50) String> formFactor;

    private List<@Size(max = 50) String> modularityType;

}
