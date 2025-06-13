package com.tisitha.product_service.dto.powerSupply;

import java.util.List;

public record PowerSupplyFilterOptionsDTO(List<String> brand,
                                          List<String> wattageOutput,
                                          List<String> certificationRating,
                                          List<String> formFactor,
                                          List<String> modularityType) {
}
