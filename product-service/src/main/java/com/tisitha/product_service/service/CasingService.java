package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.CasingRequestDTO;
import com.tisitha.product_service.dto.CasingResponseDTO;
import com.tisitha.product_service.dto.ProductPageSortDto;

import java.util.List;

public interface CasingService extends ProductService<CasingRequestDTO, CasingResponseDTO>{

    ProductPageSortDto<CasingResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> caseType, List<String> maxGPULength, List<String> includedFans);
}
