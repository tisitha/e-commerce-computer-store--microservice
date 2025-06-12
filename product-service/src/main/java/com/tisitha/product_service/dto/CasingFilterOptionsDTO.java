package com.tisitha.product_service.dto;

import java.util.List;

public record CasingFilterOptionsDTO(List<String> caseType,
                                     List<String> maxGPULength,
                                     List<String> includedFans) {
}
