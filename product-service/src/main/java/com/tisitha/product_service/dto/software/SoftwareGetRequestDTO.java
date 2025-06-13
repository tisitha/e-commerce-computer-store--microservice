package com.tisitha.product_service.dto.software;

import lombok.Data;

import java.util.List;

@Data
public class SoftwareGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> years;
    private List<String> uses;
}
