package com.tisitha.product_service.dto.motherBoard;

import lombok.Data;

import java.util.List;

@Data
public class MotherBoardGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> cpuSocket;
    private List<String> chipsetSeries;
    private List<String> formFactor;
    private List<String> ramType;
    private List<String> pcieSlotVersion;
    private List<String> m2Slots;
    private List<String> wirelessConnectivity;
}
