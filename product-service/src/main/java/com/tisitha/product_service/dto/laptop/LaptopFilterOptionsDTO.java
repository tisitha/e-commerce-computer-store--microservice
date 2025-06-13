package com.tisitha.product_service.dto.laptop;

import java.util.List;

public record LaptopFilterOptionsDTO(List<String> brand,
                                     List<String> processorBrand,
                                     List<String> processorSeries,
                                     List<String> ramCapacity,
                                     List<String> storageCapacity,
                                     List<String> displayResolution,
                                     List<String> operatingSystem,
                                     List<String> graphicsCardType,
                                     List<String> featuresIncluded) {
}
