package com.tisitha.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class InventoryDTO {

    private UUID id;
    private UUID productId;
    private Integer quantity;
}
