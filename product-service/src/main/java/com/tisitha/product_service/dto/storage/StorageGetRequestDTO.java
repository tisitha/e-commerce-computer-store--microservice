package com.tisitha.product_service.dto.storage;

import lombok.Data;

import java.util.List;

@Data
public class StorageGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
    private List<String> brand;
    private List<String> storageType;
    private List<String> capacityGB;
    private List<String> interfaceType;
    private List<String> usageType;
}
