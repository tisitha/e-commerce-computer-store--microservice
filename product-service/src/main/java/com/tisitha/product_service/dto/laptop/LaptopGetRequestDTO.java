package com.tisitha.product_service.dto.laptop;

import lombok.Data;

import java.util.List;

@Data
public class LaptopGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> processorBrand;
    private List<String> processorSeries;
    private List<String> ramCapacity;
    private List<String> storageCapacity;
    private List<String> displayResolution;
    private List<String> operatingSystem;
    private List<String> graphicsCardType;
    private List<String> featuresIncluded;
}
