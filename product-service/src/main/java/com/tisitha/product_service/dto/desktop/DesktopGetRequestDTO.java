package com.tisitha.product_service.dto.desktop;

import lombok.Data;

import java.util.List;

@Data
public class DesktopGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> productType;
    private List<String> processorBrand;
    private List<String> processorSeries;
    private List<String> gpuManufacturer;
    private List<String> gpuSeries;
    private List<String> ramCapacity;
    private List<String> storageType;
    private List<String> storageCapacity;
    private List<String> operatingSystem;
}
