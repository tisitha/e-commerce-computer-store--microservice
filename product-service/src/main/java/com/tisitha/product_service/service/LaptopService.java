package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.laptop.LaptopFilterOptionsDTO;
import com.tisitha.product_service.dto.laptop.LaptopRequestDTO;
import com.tisitha.product_service.dto.laptop.LaptopResponseDTO;

import java.util.List;

public interface LaptopService extends ProductService<LaptopRequestDTO, LaptopResponseDTO>{

    ProductPageSortDto<LaptopResponseDTO> getAll(Integer pageNumber,
                                                 Integer pageSize,
                                                 String sortBy,
                                                 String dir,
                                                 List<String> brand,
                                                 List<String> processorBrand,
                                                 List<String> processorSeries,
                                                 List<String> ramCapacity,
                                                 List<String> storageCapacity,
                                                 List<String> displayResolution,
                                                 List<String> operatingSystem,
                                                 List<String> graphicsCardType,
                                                 List<String> featuresIncluded);

    LaptopFilterOptionsDTO getAvailableFilters();
}
