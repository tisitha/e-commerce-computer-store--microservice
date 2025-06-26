package com.tisitha.product_service.dto.storage;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class StorageGetRequestDTO {

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

    private List<@Size(max = 50) String> storageType;

    private List<@Size(max = 50) String> capacityGB;

    private List<@Size(max = 50) String> interfaceType;

    private List<@Size(max = 50) String> usageType;

}
