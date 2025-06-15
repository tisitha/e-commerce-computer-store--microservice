package com.tisitha.product_service.service;


import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.monitor.MonitorFilterOptionsDTO;
import com.tisitha.product_service.dto.monitor.MonitorRequestDTO;
import com.tisitha.product_service.dto.monitor.MonitorResponseDTO;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.Monitor;
import com.tisitha.product_service.repo.MonitorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MonitorServiceImp implements MonitorService{

    private final MonitorRepository monitorRepository;
    private final InventoryClient inventoryClient;

    public MonitorServiceImp(MonitorRepository monitorRepository, InventoryClient inventoryClient) {
        this.monitorRepository = monitorRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public MonitorFilterOptionsDTO getAvailableFilters() {
        return new MonitorFilterOptionsDTO(
                monitorRepository.findDistinctBrand(),
                monitorRepository.findDistinctDisplayResolution(),
                monitorRepository.findDistinctRefreshRateHz(),
                monitorRepository.findDistinctResponseTimeMs(),
                monitorRepository.findDistinctPanelType(),
                monitorRepository.findDistinctAspectRatio(),
                monitorRepository.findDistinctAdaptiveSyncTechnology());
    }

    @Override
    public ProductPageSortDto<MonitorResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> displayResolution, List<String> refreshRateHz, List<String> responseTimeMs, List<String> panelType, List<String> aspectRatio, List<String> adaptiveSyncTechnology) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Monitor> monitorPage = monitorRepository.findAll(pageable);
        List<Monitor> monitorList1 = monitorPage.getContent();

        if(brand.isEmpty()){
            brand = monitorRepository.findDistinctBrand();
        }
        if(displayResolution.isEmpty()){
            displayResolution = monitorRepository.findDistinctDisplayResolution();
        }
        if(refreshRateHz.isEmpty()){
            refreshRateHz = monitorRepository.findDistinctRefreshRateHz();
        }
        if(responseTimeMs.isEmpty()){
            responseTimeMs = monitorRepository.findDistinctResponseTimeMs();
        }
        if(panelType.isEmpty()){
            panelType = monitorRepository.findDistinctPanelType();
        }
        if(aspectRatio.isEmpty()){
            aspectRatio = monitorRepository.findDistinctAspectRatio();
        }
        if(adaptiveSyncTechnology.isEmpty()){
            adaptiveSyncTechnology = monitorRepository.findDistinctAdaptiveSyncTechnology();
        }

        List<Monitor> monitorList2 = monitorRepository.findByBrandInAndDisplayResolutionInAndRefreshRateHzInAndResponseTimeMsInAndPanelTypeInAndAspectRatioInAndAdaptiveSyncTechnologyIn(brand,displayResolution,refreshRateHz,responseTimeMs,panelType,aspectRatio,adaptiveSyncTechnology);
        List<Monitor> monitorList = monitorList1.stream().filter(monitorList2::contains).toList();

        List<MonitorResponseDTO> dtos = monitorList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<MonitorResponseDTO>(dtos,monitorPage.getTotalElements(),monitorPage.getTotalPages(),monitorPage.isLast());
    }

    @Override
    public MonitorResponseDTO add(MonitorRequestDTO dto) {
        Monitor monitor = new Monitor();

        monitor.setName(dto.getName());
        monitor.setImgUrl(dto.getImgUrl());
        monitor.setDescription(dto.getDescription());
        monitor.setPrice(dto.getPrice());
        monitor.setNew(dto.isNew());
        monitor.setTop(dto.isTop());
        monitor.setDeal(dto.getDeal());
        monitor.setBrand(dto.getBrand());
        monitor.setDisplayResolution(dto.getDisplayResolution());
        monitor.setRefreshRateHz(dto.getRefreshRateHz());
        monitor.setResponseTimeMs(dto.getResponseTimeMs());
        monitor.setPanelType(dto.getPanelType());
        monitor.setAspectRatio(dto.getAspectRatio());
        monitor.setAdaptiveSyncTechnology(dto.getAdaptiveSyncTechnology());

        Monitor newMonitor =  monitorRepository.save(monitor);

        inventoryClient.addQuantity(newMonitor.getId(),dto.getQuantity());

        return convertToDTO(newMonitor);
    }

    MonitorResponseDTO convertToDTO(Monitor monitor ){
        return new MonitorResponseDTO(
                monitor.getId(),
                monitor.getName(),
                monitor.getImgUrl(),
                monitor.getDescription(),
                monitor.getPrice(),
                monitor.isNew(),
                monitor.isTop(),
                monitor.getDeal(),
                monitor.getBrand(),
                monitor.getDisplayResolution(),
                monitor.getRefreshRateHz(),
                monitor.getResponseTimeMs(),
                monitor.getPanelType(),
                monitor.getAspectRatio(),
                monitor.getAdaptiveSyncTechnology()
        );
    }

    @Override
    public List<MonitorResponseDTO> isNew() {
        List<Monitor> monitors = monitorRepository.findAllByIsNew(true);
        return monitors.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MonitorResponseDTO> isTop() {
        List<Monitor> monitors = monitorRepository.findAllByIsTop(true);
        return monitors.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MonitorResponseDTO> isDeal() {
        List<Monitor> monitors = monitorRepository.findAllByDealNot(0);
        return monitors.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MonitorResponseDTO> search(String text) {
        List<Monitor> monitors = monitorRepository.findByNameContainingIgnoreCase(text);
        return monitors.stream().map(this::convertToDTO).toList();
    }

    @Override
    public MonitorResponseDTO getProduct(UUID id) {
        Monitor monitor = monitorRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(monitor);
    }

    @Override
    public MonitorResponseDTO updateProduct(UUID id, MonitorRequestDTO dto) {
        Monitor monitor = monitorRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        monitor.setName(dto.getName());
        monitor.setImgUrl(dto.getImgUrl());
        monitor.setDescription(dto.getDescription());
        monitor.setPrice(dto.getPrice());
        monitor.setNew(dto.isNew());
        monitor.setTop(dto.isTop());
        monitor.setDeal(dto.getDeal());
        monitor.setBrand(dto.getBrand());
        monitor.setDisplayResolution(dto.getDisplayResolution());
        monitor.setRefreshRateHz(dto.getRefreshRateHz());
        monitor.setResponseTimeMs(dto.getResponseTimeMs());
        monitor.setPanelType(dto.getPanelType());
        monitor.setAspectRatio(dto.getAspectRatio());
        monitor.setAdaptiveSyncTechnology(dto.getAdaptiveSyncTechnology());

        Monitor newMonitor =  monitorRepository.save(monitor);

        inventoryClient.updateQuantity(newMonitor.getId(),dto.getQuantity());

        return convertToDTO(newMonitor);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(monitorRepository.existsById(id)){
            monitorRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
