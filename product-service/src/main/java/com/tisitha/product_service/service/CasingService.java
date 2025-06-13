package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.casing.CasingFilterOptionsDTO;
import com.tisitha.product_service.dto.casing.CasingRequestDTO;
import com.tisitha.product_service.dto.casing.CasingResponseDTO;

import java.util.List;

public interface CasingService extends ProductService<CasingRequestDTO, CasingResponseDTO>{

    ProductPageSortDto<CasingResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir,List<String> brand, List<String> caseType, List<String> maxGPULength, List<String> includedFans);

    CasingFilterOptionsDTO getAvailableFilters();
}
