package com.tisitha.product_service.dto.processor;

import java.util.List;

public record ProcessorFilterOptionsDTO(List<String> brand,
                                        List<String> cpuSeries,
                                        List<String> cpuSocket,
                                        List<String> coreCount,
                                        List<String> threadCount,
                                        List<String> baseClockSpeedGHz,
                                        List<String> integratedGraphics) {
}
