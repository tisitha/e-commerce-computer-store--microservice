package com.tisitha.product_service.dto.memory;

import java.util.List;

public record MemoryFilterOptionsDTO(List<String> memoryType,
                                     List<String> capacityGB,
                                     List<String> speedMHz,
                                     List<String> formFactor,
                                     List<String> rgbLighting,
                                     List<String> brand) {
}
