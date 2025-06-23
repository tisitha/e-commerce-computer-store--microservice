package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.laptop.LaptopFilterOptionsDTO;
import com.tisitha.product_service.dto.laptop.LaptopRequestDTO;
import com.tisitha.product_service.dto.laptop.LaptopResponseDTO;
import com.tisitha.product_service.exception.ProductNotFoundException;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.Laptop;
import com.tisitha.product_service.repo.LaptopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LaptopServiceImp implements LaptopService{

    private final LaptopRepository laptopRepository;
    private final InventoryClient inventoryClient;

    public LaptopServiceImp(LaptopRepository laptopRepository, InventoryClient inventoryClient) {
        this.laptopRepository = laptopRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public LaptopFilterOptionsDTO getAvailableFilters() {
        return new LaptopFilterOptionsDTO(
                laptopRepository.findDistinctBrand(),
                laptopRepository.findDistinctProcessorBrand(),
                laptopRepository.findDistinctProcessorSeries(),
                laptopRepository.findDistinctRamCapacity(),
                laptopRepository.findDistinctStorageCapacity(),
                laptopRepository.findDistinctDisplayResolution(),
                laptopRepository.findDistinctOperatingSystem(),
                laptopRepository.findDistinctGraphicsCardType(),
                laptopRepository.findDistinctFeaturesIncluded());
    }

    @Override
    public ProductPageSortDto<LaptopResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> processorBrand, List<String> processorSeries, List<String> ramCapacity, List<String> storageCapacity, List<String> displayResolution, List<String> operatingSystem, List<String> graphicsCardType, List<String> featuresIncluded) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        if(brand.isEmpty()){
            brand = laptopRepository.findDistinctBrand();
        }
        if(processorBrand.isEmpty()){
            processorBrand = laptopRepository.findDistinctProcessorBrand();
        }
        if(processorSeries.isEmpty()){
            processorSeries = laptopRepository.findDistinctProcessorSeries();
        }
        if(ramCapacity.isEmpty()){
            ramCapacity = laptopRepository.findDistinctRamCapacity();
        }
        if(storageCapacity.isEmpty()){
            storageCapacity = laptopRepository.findDistinctStorageCapacity();
        }
        if(displayResolution.isEmpty()){
            displayResolution = laptopRepository.findDistinctDisplayResolution();
        }
        if(operatingSystem.isEmpty()){
            operatingSystem = laptopRepository.findDistinctOperatingSystem();
        }
        if(graphicsCardType.isEmpty()){
            graphicsCardType = laptopRepository.findDistinctGraphicsCardType();
        }
        if(featuresIncluded.isEmpty()){
            featuresIncluded = laptopRepository.findDistinctFeaturesIncluded();
        }

        Page<Laptop> laptopPage = laptopRepository.findByBrandInAndProcessorBrandInAndProcessorSeriesInAndRamCapacityInAndStorageCapacityInAndDisplayResolutionInAndOperatingSystemInAndGraphicsCardTypeInAndFeaturesIncludedIn(brand,processorBrand,processorSeries,ramCapacity,storageCapacity,displayResolution,operatingSystem,graphicsCardType,featuresIncluded,pageable);
        List<Laptop> laptopList = laptopPage.getContent();

        List<LaptopResponseDTO> dtos = laptopList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<LaptopResponseDTO>(dtos,laptopPage.getTotalElements(),laptopPage.getTotalPages(),laptopPage.isLast());
    }

    @Override
    public LaptopResponseDTO add(LaptopRequestDTO dto) {
        Laptop laptop = new Laptop();

        laptop.setName(dto.getName());
        laptop.setImgUrl(dto.getImgUrl());
        laptop.setDescription(dto.getDescription());
        laptop.setPrice(dto.getPrice());
        laptop.setLatest(dto.isLatest());
        laptop.setTop(dto.isTop());
        laptop.setDeal(dto.getDeal());
        laptop.setBrand(dto.getBrand());
        laptop.setProcessorBrand(dto.getProcessorBrand());
        laptop.setProcessorSeries(dto.getProcessorSeries());
        laptop.setRamCapacity(dto.getRamCapacity());
        laptop.setStorageCapacity(dto.getStorageCapacity());
        laptop.setDisplayResolution(dto.getDisplayResolution());
        laptop.setOperatingSystem(dto.getOperatingSystem());
        laptop.setGraphicsCardType(dto.getGraphicsCardType());
        laptop.setFeaturesIncluded(dto.getFeaturesIncluded());

        Laptop newLaptop =  laptopRepository.save(laptop);

        inventoryClient.addQuantity(newLaptop.getId(),dto.getQuantity());

        return convertToDTO(newLaptop);
    }

    LaptopResponseDTO convertToDTO(Laptop laptop ){
        return new LaptopResponseDTO(
                laptop.getId(),
                laptop.getName(),
                laptop.getImgUrl(),
                laptop.getDescription(),
                laptop.getPrice(),
                laptop.isLatest(),
                laptop.isTop(),
                laptop.getDeal(),
                laptop.getBrand(),
                laptop.getProcessorBrand(),
                laptop.getProcessorSeries(),
                laptop.getRamCapacity(),
                laptop.getStorageCapacity(),
                laptop.getDisplayResolution(),
                laptop.getOperatingSystem(),
                laptop.getGraphicsCardType(),
                laptop.getFeaturesIncluded()
        );
    }

    @Override
    public List<LaptopResponseDTO> isNew() {
        List<Laptop> laptops = laptopRepository.findAllByLatest(true);
        return laptops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<LaptopResponseDTO> isTop() {
        List<Laptop> laptops = laptopRepository.findAllByTop(true);
        return laptops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<LaptopResponseDTO> isDeal() {
        List<Laptop> laptops = laptopRepository.findAllByDealNot(0);
        return laptops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<LaptopResponseDTO> search(String text) {
        List<Laptop> laptops = laptopRepository.findByNameContainingIgnoreCase(text);
        return laptops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public LaptopResponseDTO getProduct(UUID id) {
        Laptop laptop = laptopRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));
        return convertToDTO(laptop);
    }

    @Override
    public LaptopResponseDTO updateProduct(UUID id, LaptopRequestDTO dto) {
        Laptop laptop = laptopRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));

        laptop.setName(dto.getName());
        laptop.setImgUrl(dto.getImgUrl());
        laptop.setDescription(dto.getDescription());
        laptop.setPrice(dto.getPrice());
        laptop.setLatest(dto.isLatest());
        laptop.setTop(dto.isTop());
        laptop.setDeal(dto.getDeal());
        laptop.setBrand(dto.getBrand());
        laptop.setProcessorBrand(dto.getProcessorBrand());
        laptop.setProcessorSeries(dto.getProcessorSeries());
        laptop.setRamCapacity(dto.getRamCapacity());
        laptop.setStorageCapacity(dto.getStorageCapacity());
        laptop.setDisplayResolution(dto.getDisplayResolution());
        laptop.setOperatingSystem(dto.getOperatingSystem());
        laptop.setGraphicsCardType(dto.getGraphicsCardType());
        laptop.setFeaturesIncluded(dto.getFeaturesIncluded());

        Laptop newLaptop =  laptopRepository.save(laptop);

        inventoryClient.updateQuantity(newLaptop.getId(),dto.getQuantity());

        return convertToDTO(newLaptop);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(laptopRepository.existsById(id)){
            laptopRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new ProductNotFoundException("Product id:"+id+" is invalid");
        }
    }

}
