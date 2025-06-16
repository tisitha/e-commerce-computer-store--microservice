package com.tisitha.product_service.dto.motherBoard;

import lombok.Data;

@Data
public class MotherBoardRequestDTO {

    private String name;
    private String imgUrl;
    private String description;
    private double price;
    private boolean latest;
    private boolean top;
    private double deal;

    private String brand;
    private String cpuSocket;
    private String chipsetSeries;
    private String formFactor;
    private String ramType;
    private String pcieSlotVersion;
    private String m2Slots;
    private String wirelessConnectivity;

    private Integer quantity;
}
