package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.processor.ProcessorFilterOptionsDTO;
import com.tisitha.product_service.dto.processor.ProcessorRequestDTO;
import com.tisitha.product_service.dto.processor.ProcessorResponseDTO;

import java.util.List;

public interface ProcessorService extends ProductService<ProcessorRequestDTO, ProcessorResponseDTO>{

    ProductPageSortDto<ProcessorResponseDTO> getAll(Integer pageNumber,
                                                    Integer pageSize,
                                                    String sortBy,
                                                    String dir,
                                                    List<String> brand,
                                                    List<String> cpuSeries,
                                                    List<String> cpuSocket,
                                                    List<String> coreCount,
                                                    List<String> threadCount,
                                                    List<String> baseClockSpeedGHz,
                                                    List<String> integratedGraphics);

    ProcessorFilterOptionsDTO getAvailableFilters();
}
