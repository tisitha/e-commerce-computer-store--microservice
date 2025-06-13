package com.tisitha.product_service.dto.processor;

import lombok.Data;

import java.util.List;

@Data
public class ProcessorGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> cpuSeries;
    private List<String> cpuSocket;
    private List<String> coreCount;
    private List<String> threadCount;
    private List<String> baseClockSpeedGHz;
    private List<String> integratedGraphics;
}
