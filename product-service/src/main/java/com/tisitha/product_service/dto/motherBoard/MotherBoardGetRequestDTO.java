package com.tisitha.product_service.dto.motherBoard;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class MotherBoardGetRequestDTO {

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

    private List<@Size(max = 50) String> cpuSocket;

    private List<@Size(max = 50) String> chipsetSeries;

    private List<@Size(max = 50) String> formFactor;

    private List<@Size(max = 50) String> ramType;

    private List<@Size(max = 50) String> pcieSlotVersion;

    private List<@Size(max = 50) String> m2Slots;

    private List<@Size(max = 50) String> wirelessConnectivity;

}
