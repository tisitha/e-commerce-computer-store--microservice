package com.tisitha.product_service.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StorageResponseDTO {

    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
    private double deal;
    private String category;

    private String brand;
    private String storageType;
    private String capacityGB;
    private String interfaceType;
    private String usageType;

    private Integer quantity;
}
