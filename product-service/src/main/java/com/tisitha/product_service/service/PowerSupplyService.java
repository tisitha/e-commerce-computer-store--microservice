package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyFilterOptionsDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyRequestDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyResponseDTO;

import java.util.List;

public interface PowerSupplyService extends ProductService<PowerSupplyRequestDTO, PowerSupplyResponseDTO>{

    ProductPageSortDto<PowerSupplyResponseDTO> getAll(Integer pageNumber,
                                                      Integer pageSize,
                                                      String sortBy,
                                                      String dir,
                                                      List<String> brand,
                                                      List<String> wattageOutput,
                                                      List<String> certificationRating,
                                                      List<String> formFactor,
                                                      List<String> modularityType);

    PowerSupplyFilterOptionsDTO getAvailableFilters();
}
