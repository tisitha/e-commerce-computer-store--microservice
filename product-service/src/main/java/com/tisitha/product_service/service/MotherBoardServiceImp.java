package com.tisitha.product_service.service;


import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.motherBoard.MotherBoardFilterOptionsDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardRequestDTO;
import com.tisitha.product_service.dto.motherBoard.MotherBoardResponseDTO;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.MotherBoard;
import com.tisitha.product_service.repo.MotherBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MotherBoardServiceImp implements MotherBoardService{

    private final MotherBoardRepository motherBoardRepository;
    private final InventoryClient inventoryClient;

    public MotherBoardServiceImp(MotherBoardRepository motherBoardRepository, InventoryClient inventoryClient) {
        this.motherBoardRepository = motherBoardRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public MotherBoardFilterOptionsDTO getAvailableFilters() {
        return new MotherBoardFilterOptionsDTO(
                motherBoardRepository.findDistinctBrand(),
                motherBoardRepository.findDistinctCpuSocket(),
                motherBoardRepository.findDistinctChipsetSeries(),
                motherBoardRepository.findDistinctFormFactor(),
                motherBoardRepository.findDistinctRamType(),
                motherBoardRepository.findDistinctPcieSlotVersion(),
                motherBoardRepository.findDistinctM2Slots(),
                motherBoardRepository.findDistinctWirelessConnectivity());
    }

    @Override
    public ProductPageSortDto<MotherBoardResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> cpuSocket, List<String> chipsetSeries, List<String> formFactor, List<String> ramType, List<String> pcieSlotVersion, List<String> m2Slots, List<String> wirelessConnectivity) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<MotherBoard> motherBoardPage = motherBoardRepository.findAll(pageable);
        List<MotherBoard> motherBoardList1 = motherBoardPage.getContent();

        if(brand.isEmpty()){
            brand = motherBoardRepository.findDistinctBrand();
        }
        if(cpuSocket.isEmpty()){
            cpuSocket = motherBoardRepository.findDistinctCpuSocket();
        }
        if(chipsetSeries.isEmpty()){
            chipsetSeries = motherBoardRepository.findDistinctChipsetSeries();
        }
        if(formFactor.isEmpty()){
            formFactor = motherBoardRepository.findDistinctFormFactor();
        }
        if(ramType.isEmpty()){
            ramType = motherBoardRepository.findDistinctRamType();
        }
        if(pcieSlotVersion.isEmpty()){
            pcieSlotVersion = motherBoardRepository.findDistinctPcieSlotVersion();
        }
        if(m2Slots.isEmpty()){
            m2Slots = motherBoardRepository.findDistinctM2Slots();
        }
        if(wirelessConnectivity.isEmpty()){
            wirelessConnectivity = motherBoardRepository.findDistinctWirelessConnectivity();
        }

        List<MotherBoard> motherBoardList2 = motherBoardRepository.findByBrandInAndCpuSocketInAndChipsetSeriesInAndFormFactorInAndRamTypeInAndPcieSlotVersionInAndM2SlotsInAndWirelessConnectivityIn(brand,cpuSocket,chipsetSeries,formFactor,ramType,pcieSlotVersion,m2Slots,wirelessConnectivity);
        List<MotherBoard> motherBoardList = motherBoardList1.stream().filter(motherBoardList2::contains).toList();

        List<MotherBoardResponseDTO> dtos = motherBoardList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<MotherBoardResponseDTO>(dtos,motherBoardPage.getTotalElements(),motherBoardPage.getTotalPages(),motherBoardPage.isLast());
    }

    @Override
    public MotherBoardResponseDTO add(MotherBoardRequestDTO dto) {
        MotherBoard motherBoard = new MotherBoard();

        motherBoard.setName(dto.getName());
        motherBoard.setImgUrl(dto.getImgUrl());
        motherBoard.setDescription(dto.getDescription());
        motherBoard.setPrice(dto.getPrice());
        motherBoard.setNew(dto.isNew());
        motherBoard.setTop(dto.isTop());
        motherBoard.setDeal(dto.getDeal());
        motherBoard.setBrand(dto.getBrand());
        motherBoard.setCpuSocket(dto.getCpuSocket());
        motherBoard.setChipsetSeries(dto.getChipsetSeries());
        motherBoard.setFormFactor(dto.getFormFactor());
        motherBoard.setRamType(dto.getRamType());
        motherBoard.setPcieSlotVersion(dto.getPcieSlotVersion());
        motherBoard.setM2Slots(dto.getM2Slots());
        motherBoard.setWirelessConnectivity(dto.getWirelessConnectivity());

        MotherBoard newMotherBoard =  motherBoardRepository.save(motherBoard);

        inventoryClient.addQuantity(newMotherBoard.getId(),dto.getQuantity());

        return convertToDTO(newMotherBoard);
    }

    MotherBoardResponseDTO convertToDTO(MotherBoard motherBoard ){
        return new MotherBoardResponseDTO(
                motherBoard.getId(),
                motherBoard.getName(),
                motherBoard.getImgUrl(),
                motherBoard.getDescription(),
                motherBoard.getPrice(),
                motherBoard.isNew(),
                motherBoard.isTop(),
                motherBoard.getDeal(),
                motherBoard.getBrand(),
                motherBoard.getCpuSocket(),
                motherBoard.getChipsetSeries(),
                motherBoard.getFormFactor(),
                motherBoard.getRamType(),
                motherBoard.getPcieSlotVersion(),
                motherBoard.getM2Slots(),
                motherBoard.getWirelessConnectivity()
        );
    }

    @Override
    public List<MotherBoardResponseDTO> isNew() {
        List<MotherBoard> motherBoards = motherBoardRepository.findAllByIsNew(true);
        return motherBoards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MotherBoardResponseDTO> isTop() {
        List<MotherBoard> motherBoards = motherBoardRepository.findAllByIsTop(true);
        return motherBoards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MotherBoardResponseDTO> isDeal() {
        List<MotherBoard> motherBoards = motherBoardRepository.findAllByDealNot(0);
        return motherBoards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MotherBoardResponseDTO> search(String text) {
        List<MotherBoard> motherBoards = motherBoardRepository.findByNameContainingIgnoreCase(text);
        return motherBoards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public MotherBoardResponseDTO getProduct(UUID id) {
        MotherBoard motherBoard = motherBoardRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(motherBoard);
    }

    @Override
    public MotherBoardResponseDTO updateProduct(UUID id, MotherBoardRequestDTO dto) {
        MotherBoard motherBoard = motherBoardRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        motherBoard.setName(dto.getName());
        motherBoard.setImgUrl(dto.getImgUrl());
        motherBoard.setDescription(dto.getDescription());
        motherBoard.setPrice(dto.getPrice());
        motherBoard.setNew(dto.isNew());
        motherBoard.setTop(dto.isTop());
        motherBoard.setDeal(dto.getDeal());
        motherBoard.setBrand(dto.getBrand());
        motherBoard.setCpuSocket(dto.getCpuSocket());
        motherBoard.setChipsetSeries(dto.getChipsetSeries());
        motherBoard.setFormFactor(dto.getFormFactor());
        motherBoard.setRamType(dto.getRamType());
        motherBoard.setPcieSlotVersion(dto.getPcieSlotVersion());
        motherBoard.setM2Slots(dto.getM2Slots());
        motherBoard.setWirelessConnectivity(dto.getWirelessConnectivity());

        MotherBoard newMotherBoard =  motherBoardRepository.save(motherBoard);

        inventoryClient.updateQuantity(newMotherBoard.getId(),dto.getQuantity());

        return convertToDTO(newMotherBoard);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(motherBoardRepository.existsById(id)){
            motherBoardRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
