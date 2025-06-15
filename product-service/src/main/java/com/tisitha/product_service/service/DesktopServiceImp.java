package com.tisitha.product_service.service;


import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.desktop.DesktopFilterOptionsDTO;
import com.tisitha.product_service.dto.desktop.DesktopRequestDTO;
import com.tisitha.product_service.dto.desktop.DesktopResponseDTO;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.Desktop;
import com.tisitha.product_service.repo.DesktopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DesktopServiceImp implements DesktopService{

    private final DesktopRepository desktopRepository;
    private final InventoryClient inventoryClient;

    public DesktopServiceImp(DesktopRepository desktopRepository, InventoryClient inventoryClient) {
        this.desktopRepository = desktopRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public DesktopFilterOptionsDTO getAvailableFilters() {
        return new DesktopFilterOptionsDTO(
                desktopRepository.findDistinctBrand(),
                desktopRepository.findDistinctProductType(),
                desktopRepository.findDistinctProcessorBrand(),
                desktopRepository.findDistinctProcessorSeries(),
                desktopRepository.findDistinctGpuManufacturer(),
                desktopRepository.findDistinctGpuSeries(),
                desktopRepository.findDistinctRamCapacity(),
                desktopRepository.findDistinctStorageType(),
                desktopRepository.findDistinctStorageCapacity(),
                desktopRepository.findDistinctOperatingSystem());
    }

    @Override
    public ProductPageSortDto<DesktopResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> productType, List<String> processorBrand, List<String> processorSeries, List<String> gpuManufacturer, List<String> gpuSeries, List<String> ramCapacity, List<String> storageType, List<String> storageCapacity, List<String> operatingSystem) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Desktop> desktopPage = desktopRepository.findAll(pageable);
        List<Desktop> desktopList1 = desktopPage.getContent();

        if(brand.isEmpty()){
            brand = desktopRepository.findDistinctBrand();
        }
        if(productType.isEmpty()){
            productType = desktopRepository.findDistinctProductType();
        }
        if(processorBrand.isEmpty()){
            processorBrand = desktopRepository.findDistinctProcessorBrand();
        }
        if(processorSeries.isEmpty()){
            processorSeries = desktopRepository.findDistinctProcessorSeries();
        }
        if(gpuManufacturer.isEmpty()){
            gpuManufacturer = desktopRepository.findDistinctGpuManufacturer();
        }
        if(gpuSeries.isEmpty()){
            gpuSeries = desktopRepository.findDistinctGpuSeries();
        }
        if(ramCapacity.isEmpty()){
            ramCapacity = desktopRepository.findDistinctRamCapacity();
        }
        if(storageType.isEmpty()){
            storageType = desktopRepository.findDistinctStorageType();
        }
        if(storageCapacity.isEmpty()){
            storageCapacity = desktopRepository.findDistinctStorageCapacity();
        }
        if(operatingSystem.isEmpty()){
            operatingSystem = desktopRepository.findDistinctOperatingSystem();
        }

        List<Desktop> desktopList2 = desktopRepository.findByBrandInAndProductTypeInAndProcessorBrandInAndProcessorSeriesInAndGpuManufacturerInAndGpuSeriesInAndRamCapacityInAndStorageTypeInAndStorageCapacityInAndOperatingSystemIn(brand,productType,processorBrand,processorSeries,gpuManufacturer,gpuSeries,ramCapacity,storageType,storageCapacity,operatingSystem);
        List<Desktop> desktopList = desktopList1.stream().filter(desktopList2::contains).toList();

        List<DesktopResponseDTO> dtos = desktopList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<DesktopResponseDTO>(dtos,desktopPage.getTotalElements(),desktopPage.getTotalPages(),desktopPage.isLast());
    }

    @Override
    public DesktopResponseDTO add(DesktopRequestDTO dto) {
        Desktop desktop = new Desktop();

        desktop.setName(dto.getName());
        desktop.setImgUrl(dto.getImgUrl());
        desktop.setDescription(dto.getDescription());
        desktop.setPrice(dto.getPrice());
        desktop.setNew(dto.isNew());
        desktop.setTop(dto.isTop());
        desktop.setDeal(dto.getDeal());
        desktop.setBrand(dto.getBrand());
        desktop.setProductType(dto.getProductType());
        desktop.setProcessorBrand(dto.getProcessorBrand());
        desktop.setGpuManufacturer(dto.getGpuManufacturer());
        desktop.setGpuSeries(dto.getGpuSeries());
        desktop.setRamCapacity(dto.getRamCapacity());
        desktop.setStorageType(dto.getStorageType());
        desktop.setRamCapacity(dto.getRamCapacity());
        desktop.setOperatingSystem(dto.getOperatingSystem());

        Desktop newDesktop =  desktopRepository.save(desktop);

        inventoryClient.addQuantity(newDesktop.getId(),dto.getQuantity());

        return convertToDTO(newDesktop);
    }

    DesktopResponseDTO convertToDTO(Desktop desktop ){
        return new DesktopResponseDTO(
                desktop.getId(),
                desktop.getName(),
                desktop.getImgUrl(),
                desktop.getDescription(),
                desktop.getPrice(),
                desktop.isNew(),
                desktop.isTop(),
                desktop.getDeal(),
                desktop.getBrand(),
                desktop.getProductType(),
                desktop.getProcessorBrand(),
                desktop.getProcessorSeries(),
                desktop.getGpuManufacturer(),
                desktop.getGpuSeries(),
                desktop.getRamCapacity(),
                desktop.getStorageType(),
                desktop.getStorageCapacity(),
                desktop.getOperatingSystem()
        );
    }

    @Override
    public List<DesktopResponseDTO> isNew() {
        List<Desktop> desktops = desktopRepository.findAllByIsNew(true);
        return desktops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<DesktopResponseDTO> isTop() {
        List<Desktop> desktops = desktopRepository.findAllByIsTop(true);
        return desktops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<DesktopResponseDTO> isDeal() {
        List<Desktop> desktops = desktopRepository.findAllByDealNot(0);
        return desktops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<DesktopResponseDTO> search(String text) {
        List<Desktop> desktops = desktopRepository.findByNameContainingIgnoreCase(text);
        return desktops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public DesktopResponseDTO getProduct(UUID id) {
        Desktop desktop = desktopRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(desktop);
    }

    @Override
    public DesktopResponseDTO updateProduct(UUID id, DesktopRequestDTO dto) {
        Desktop desktop = desktopRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        desktop.setName(dto.getName());
        desktop.setImgUrl(dto.getImgUrl());
        desktop.setDescription(dto.getDescription());
        desktop.setPrice(dto.getPrice());
        desktop.setNew(dto.isNew());
        desktop.setTop(dto.isTop());
        desktop.setDeal(dto.getDeal());
        desktop.setBrand(dto.getBrand());
        desktop.setProductType(dto.getProductType());
        desktop.setProcessorBrand(dto.getProcessorBrand());
        desktop.setGpuManufacturer(dto.getGpuManufacturer());
        desktop.setGpuSeries(dto.getGpuSeries());
        desktop.setRamCapacity(dto.getRamCapacity());
        desktop.setStorageType(dto.getStorageType());
        desktop.setRamCapacity(dto.getRamCapacity());
        desktop.setOperatingSystem(dto.getOperatingSystem());

        Desktop newDesktop =  desktopRepository.save(desktop);

        inventoryClient.updateQuantity(newDesktop.getId(),dto.getQuantity());

        return convertToDTO(newDesktop);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(desktopRepository.existsById(id)){
            desktopRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
