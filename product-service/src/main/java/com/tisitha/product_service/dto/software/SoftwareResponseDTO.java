package com.tisitha.product_service.dto.software;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SoftwareResponseDTO {

    private UUID id;

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean isNew;
    private boolean isTop;
    private double deal;

    private String brand;
    private String years;
    private String uses;
}
