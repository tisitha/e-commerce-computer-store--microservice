package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.graphicCard.GraphicCardFilterOptionsDTO;
import com.tisitha.product_service.dto.graphicCard.GraphicCardRequestDTO;
import com.tisitha.product_service.dto.graphicCard.GraphicCardResponseDTO;

import java.util.List;

public interface GraphicCardService extends ProductService<GraphicCardRequestDTO, GraphicCardResponseDTO>{

    ProductPageSortDto<GraphicCardResponseDTO> getAll(Integer pageNumber,
                                                      Integer pageSize,
                                                      String sortBy,
                                                      String dir,
                                                      List<String> gpuManufacturer,
                                                      List<String> gpuSeries,
                                                      List<String> vRamCapacity);

    GraphicCardFilterOptionsDTO getAvailableFilters();
}
