package com.tisitha.product_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Processor extends Product{

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String cpuSeries;

    @Column(nullable = false)
    private String cpuSocket;

    @Column(nullable = false)
    private String coreCount;

    @Column(nullable = false)
    private String threadCount;

    @Column(nullable = false)
    private String baseClockSpeedGHz;

    @Column(nullable = false)
    private String integratedGraphics;

}
