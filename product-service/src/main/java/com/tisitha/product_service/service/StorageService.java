package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.storage.StorageFilterOptionsDTO;
import com.tisitha.product_service.dto.storage.StorageRequestDTO;
import com.tisitha.product_service.dto.storage.StorageResponseDTO;

import java.util.List;

public interface StorageService extends ProductService<StorageRequestDTO, StorageResponseDTO>{

    ProductPageSortDto<StorageResponseDTO> getAll(Integer pageNumber,
                                                  Integer pageSize,
                                                  String sortBy,
                                                  String dir,
                                                  List<String> brand,
                                                  List<String> storageType,
                                                  List<String> capacityGB,
                                                  List<String> interfaceType,
                                                  List<String> usageType);

    StorageFilterOptionsDTO getAvailableFilters();
}
