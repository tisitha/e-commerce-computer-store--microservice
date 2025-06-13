package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.desktop.DesktopFilterOptionsDTO;
import com.tisitha.product_service.dto.desktop.DesktopRequestDTO;
import com.tisitha.product_service.dto.desktop.DesktopResponseDTO;

import java.util.List;

public interface DesktopService extends ProductService<DesktopRequestDTO, DesktopResponseDTO>{

    ProductPageSortDto<DesktopResponseDTO> getAll(Integer pageNumber,
                                                  Integer pageSize,
                                                  String sortBy,
                                                  String dir,
                                                  List<String> brand,
                                                  List<String> productType,
                                                  List<String> processorBrand,
                                                  List<String> processorSeries,
                                                  List<String> gpuManufacturer,
                                                  List<String> gpuSeries,
                                                  List<String> ramCapacity,
                                                  List<String> storageType,
                                                  List<String> storageCapacity,
                                                  List<String> operatingSystem);

    DesktopFilterOptionsDTO getAvailableFilters();
}
