package com.tisitha.product_service.dto.graphicsCard;

import lombok.Data;

import java.util.List;

@Data
public class GraphicsCardGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> gpuManufacturer;
    private List<String> gpuSeries;
    private List<String> vramGb;

}
