package com.tisitha.product_service.dto.memory;

import lombok.Data;

import java.util.List;

@Data
public class MemoryGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> memoryType;
    private List<String> capacityGB;
    private List<String> speedMHz;
    private List<String> formFactor;
    private List<String> rgbLighting;
    private List<String> brand;
}
