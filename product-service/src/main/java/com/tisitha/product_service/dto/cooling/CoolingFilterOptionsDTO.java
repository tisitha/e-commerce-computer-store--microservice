package com.tisitha.product_service.dto.cooling;

import java.util.List;

public record CoolingFilterOptionsDTO(List<String> brand,
                                      List<String> coolingType,
                                      List<String> socketCompatibility,
                                      List<String> fanSize,
                                      List<String> rgbLighting) {
}
