package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.peripheral.PeripheralFilterOptionsDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralRequestDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralResponseDTO;

import java.util.List;

public interface PeripheralService extends ProductService<PeripheralRequestDTO, PeripheralResponseDTO>{

    ProductPageSortDto<PeripheralResponseDTO> getAll(Integer pageNumber,
                                                     Integer pageSize,
                                                     String sortBy,
                                                     String dir,
                                                     List<String> brand,
                                                     List<String> peripheralType,
                                                     List<String> connectivityType,
                                                     List<String> rgbLighting);

    PeripheralFilterOptionsDTO getAvailableFilters();
}
