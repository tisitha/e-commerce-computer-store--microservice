package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.software.SoftwareFilterOptionsDTO;
import com.tisitha.product_service.dto.software.SoftwareRequestDTO;
import com.tisitha.product_service.dto.software.SoftwareResponseDTO;

import java.util.List;

public interface SoftwareService extends ProductService<SoftwareRequestDTO, SoftwareResponseDTO>{

    ProductPageSortDto<SoftwareResponseDTO> getAll(Integer pageNumber,
                                                   Integer pageSize,
                                                   String sortBy,
                                                   String dir,
                                                   List<String> brand,
                                                   List<String> years,
                                                   List<String> uses);

    SoftwareFilterOptionsDTO getAvailableFilters();
}
