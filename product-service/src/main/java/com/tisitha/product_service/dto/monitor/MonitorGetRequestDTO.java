package com.tisitha.product_service.dto.monitor;

import lombok.Data;

import java.util.List;

@Data
public class MonitorGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> displayResolution;
    private List<String> refreshRateHz;
    private List<String> responseTimeMs;
    private List<String> panelType;
    private List<String> aspectRatio;
    private List<String> adaptiveSyncTechnology;
}
