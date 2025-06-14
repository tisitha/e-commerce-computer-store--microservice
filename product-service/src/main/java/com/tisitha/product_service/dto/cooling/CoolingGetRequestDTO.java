package com.tisitha.product_service.dto.cooling;

import lombok.Data;

import java.util.List;

@Data
public class CoolingGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> coolingType;
    private List<String> socketCompatibility;
    private List<String> fanSize;
    private List<String> rgbLighting;
}
