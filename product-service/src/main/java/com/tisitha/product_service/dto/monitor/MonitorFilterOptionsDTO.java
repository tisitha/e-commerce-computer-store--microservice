package com.tisitha.product_service.dto.monitor;

import java.util.List;

public record MonitorFilterOptionsDTO(List<String> brand,
                                      List<String> displayResolution,
                                      List<String> refreshRateHz,
                                      List<String> responseTimeMs,
                                      List<String> panelType,
                                      List<String> aspectRatio,
                                      List<String> adaptiveSyncTechnology) {
}
