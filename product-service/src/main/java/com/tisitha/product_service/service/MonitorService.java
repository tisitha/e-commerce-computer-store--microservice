package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.monitor.MonitorFilterOptionsDTO;
import com.tisitha.product_service.dto.monitor.MonitorRequestDTO;
import com.tisitha.product_service.dto.monitor.MonitorResponseDTO;

import java.util.List;

public interface MonitorService extends ProductService<MonitorRequestDTO, MonitorResponseDTO>{

    ProductPageSortDto<MonitorResponseDTO> getAll(Integer pageNumber,
                                                  Integer pageSize,
                                                  String sortBy,
                                                  String dir,
                                                  List<String> brand,
                                                  List<String> displayResolution,
                                                  List<String> refreshRateHz,
                                                  List<String> responseTimeMs,
                                                  List<String> panelType,
                                                  List<String> aspectRatio,
                                                  List<String> adaptiveSyncTechnology);

    MonitorFilterOptionsDTO getAvailableFilters();
}
