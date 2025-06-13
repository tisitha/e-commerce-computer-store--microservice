package com.tisitha.product_service.dto.motherBoard;

import java.util.List;

public record MotherBoardFilterOptionsDTO(List<String> brand,
                                          List<String> cpuSocket,
                                          List<String> chipsetSeries,
                                          List<String> formFactor,
                                          List<String> ramType,
                                          List<String> pcieSlotVersion,
                                          List<String> m2Slots,
                                          List<String> wirelessConnectivity) {
}
