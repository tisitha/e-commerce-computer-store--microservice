package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.motherBoard.MotherBoardFilterOptionsDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardRequestDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardResponseDTO;

import java.util.List;

public interface MotherBoardService extends ProductService<MotherBoardRequestDTO, MotherBoardResponseDTO>{

    ProductPageSortDto<MotherBoardResponseDTO> getAll(Integer pageNumber,
                                                      Integer pageSize,
                                                      String sortBy,
                                                      String dir,
                                                      List<String> brand,
                                                      List<String> cpuSocket,
                                                      List<String> chipsetSeries,
                                                      List<String> formFactor,
                                                      List<String> ramType,
                                                      List<String> pcieSlotVersion,
                                                      List<String> m2Slots,
                                                      List<String> wirelessConnectivity);

    MotherBoardFilterOptionsDTO getAvailableFilters();
}
