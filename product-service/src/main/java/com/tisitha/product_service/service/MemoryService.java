package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.memory.MemoryFilterOptionsDTO;
import com.tisitha.product_service.dto.memory.MemoryRequestDTO;
import com.tisitha.product_service.dto.memory.MemoryResponseDTO;

import java.util.List;

public interface MemoryService extends ProductService<MemoryRequestDTO, MemoryResponseDTO>{

    ProductPageSortDto<MemoryResponseDTO> getAll(Integer pageNumber,
                                                 Integer pageSize,
                                                 String sortBy,
                                                 String dir,
                                                 List<String> memoryType,
                                                 List<String> capacityGB,
                                                 List<String> speedMHz,
                                                 List<String> formFactor,
                                                 List<String> rgbLighting,
                                                 List<String> brand);

    MemoryFilterOptionsDTO getAvailableFilters();
}
