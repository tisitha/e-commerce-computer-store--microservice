package com.tisitha.product_service.dto.peripheral;

import lombok.Data;

import java.util.List;

@Data
public class PeripheralGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> peripheralType;
    private List<String> connectivityType;
    private List<String> rgbLighting;
}
