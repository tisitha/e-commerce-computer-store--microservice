package com.tisitha.product_service.dto.casing;

import java.util.List;

public record CasingFilterOptionsDTO(List<String> brand,
                                     List<String> caseType,
                                     List<String> maxGPULength,
                                     List<String> includedFans) {
}
