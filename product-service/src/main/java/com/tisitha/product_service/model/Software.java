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
public class Software extends Product{

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String years;

    @Column(nullable = false)
    private String uses;
}
