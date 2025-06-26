package com.tisitha.order_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class CartItemRequestDTO {

    @NotNull
    private UUID productId;

    @NotBlank
    @Size(max = 250)
    private String title;

    private String imgUrl;

    @DecimalMin(value = "0.00", inclusive = true)
    private double price;

    @DecimalMin(value = "0.00", inclusive = true)
    private double deal;

    @NotNull
    @Min(0)
    private int quantity;

    @NotNull
    private UUID customerId;
}
