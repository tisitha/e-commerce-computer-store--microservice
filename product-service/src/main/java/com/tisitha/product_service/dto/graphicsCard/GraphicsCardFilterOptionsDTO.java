package com.tisitha.product_service.dto.graphicsCard;

import java.util.List;

public record GraphicsCardFilterOptionsDTO(List<String> gpuManufacturer,
                                           List<String> gpuSeries,
                                           List<String> vramGb) {
}
