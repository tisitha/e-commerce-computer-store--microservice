package com.tisitha.product_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CasingGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> caseType;
    private List<String> maxGPULength;
    private List<String> includedFans;
}
