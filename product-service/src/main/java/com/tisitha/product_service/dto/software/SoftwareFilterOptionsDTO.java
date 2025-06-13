package com.tisitha.product_service.dto.software;

import java.util.List;

public record SoftwareFilterOptionsDTO(List<String> brand,
                                       List<String> years,
                                       List<String> uses) {
}
