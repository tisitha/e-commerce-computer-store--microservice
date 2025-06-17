package com.tisitha.order_service.dto;

import lombok.Data;


@Data
public class OrderGetRequestDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String dir;
}
