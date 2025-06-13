package com.tisitha.product_service.dto.powerSupply;

import lombok.Data;

import java.util.List;

@Data
public class PowerSupplyGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> wattageOutput;
    private List<String> certificationRating;
    private List<String> formFactor;
    private List<String> modularityType;
}
