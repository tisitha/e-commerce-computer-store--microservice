package com.tisitha.product_service.dto.peripheral;

import java.util.List;

public record PeripheralFilterOptionsDTO(List<String> brand,
                                         List<String> peripheralType,
                                         List<String> connectivityType,
                                         List<String> rgbLighting) {
}
