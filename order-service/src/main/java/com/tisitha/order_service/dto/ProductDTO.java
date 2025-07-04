package com.tisitha.order_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDTO {

    private UUID id;

    private String name;

    private String imgUrl;

    private String description;

    private double price;

    private boolean latest;

    private boolean top;

    private double deal;

    private String category;
}
