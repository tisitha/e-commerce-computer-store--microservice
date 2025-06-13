package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.cooling.CoolingFilterOptionsDTO;
import com.tisitha.product_service.dto.cooling.CoolingRequestDTO;
import com.tisitha.product_service.dto.cooling.CoolingResponseDTO;

import java.util.List;

public interface CoolingService extends ProductService<CoolingRequestDTO, CoolingResponseDTO>{

    ProductPageSortDto<CoolingResponseDTO> getAll(Integer pageNumber,
                                                  Integer pageSize,
                                                  String sortBy,
                                                  String dir,
                                                  List<String> brand,
                                                  List<String> coolingType,
                                                  List<String> socketCompatibility,
                                                  List<String> fanSize,
                                                  List<String> rgbLighting);

    CoolingFilterOptionsDTO getAvailableFilters();
}
