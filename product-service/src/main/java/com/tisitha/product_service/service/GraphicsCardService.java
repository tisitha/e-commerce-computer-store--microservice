package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardFilterOptionsDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardRequestDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardResponseDTO;

import java.util.List;

public interface GraphicsCardService extends ProductService<GraphicsCardRequestDTO, GraphicsCardResponseDTO>{

    ProductPageSortDto<GraphicsCardResponseDTO> getAll(Integer pageNumber,
                                                       Integer pageSize,
                                                       String sortBy,
                                                       String dir,
                                                       List<String> gpuManufacturer,
                                                       List<String> gpuSeries,
                                                       List<String> vRamCapacity);

    GraphicsCardFilterOptionsDTO getAvailableFilters();
}
