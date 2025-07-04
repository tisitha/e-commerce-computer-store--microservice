package com.tisitha.order_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class CartItemRequestDTO {

    @NotNull
    private UUID productId;

    @NotBlank
    private String category;

    @NotNull
    @Min(0)
    private int quantity;
}
