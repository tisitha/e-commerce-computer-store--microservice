package com.tisitha.product_service.dto.storage;

import java.util.List;

public record StorageFilterOptionsDTO(List<String> brand,
                                      List<String> storageType,
                                      List<String> capacityGB,
                                      List<String> interfaceType,
                                      List<String> usageType) {
}
