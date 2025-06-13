package com.tisitha.product_service.dto.desktop;

import java.util.List;

public record DesktopFilterOptionsDTO(List<String> brand,
                                      List<String> productType,
                                      List<String> processorBrand,
                                      List<String> processorSeries,
                                      List<String> gpuManufacturer,
                                      List<String> gpuSeries,
                                      List<String> ramCapacity,
                                      List<String> storageType,
                                      List<String> storageCapacity,
                                      List<String> operatingSystem) {
}
