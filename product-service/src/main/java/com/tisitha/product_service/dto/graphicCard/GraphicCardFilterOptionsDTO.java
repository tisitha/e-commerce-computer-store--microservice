package com.tisitha.product_service.dto.graphicCard;

import java.util.List;

public record GraphicCardFilterOptionsDTO(List<String> gpuManufacturer,
                                          List<String> gpuSeries,
                                          List<String> vRamCapacity) {
}
