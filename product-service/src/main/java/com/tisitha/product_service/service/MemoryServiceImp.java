package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.memory.MemoryFilterOptionsDTO;
import com.tisitha.product_service.dto.memory.MemoryRequestDTO;
import com.tisitha.product_service.dto.memory.MemoryResponseDTO;
import com.tisitha.product_service.exception.ProductNotFoundException;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.Memory;
import com.tisitha.product_service.repo.MemoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemoryServiceImp implements MemoryService{

    private final MemoryRepository memoryRepository;
    private final InventoryClient inventoryClient;

    public MemoryServiceImp(MemoryRepository memoryRepository, InventoryClient inventoryClient) {
        this.memoryRepository = memoryRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public MemoryFilterOptionsDTO getAvailableFilters() {
        return new MemoryFilterOptionsDTO(
                memoryRepository.findDistinctMemoryType(),
                memoryRepository.findDistinctCapacityGB(),
                memoryRepository.findDistinctSpeedMHz(),
                memoryRepository.findDistinctFormFactor(),
                memoryRepository.findDistinctRgbLighting(),
                memoryRepository.findDistinctBrand());
    }

    @Override
    public ProductPageSortDto<MemoryResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> memoryType, List<String> capacityGB, List<String> speedMHz, List<String> formFactor, List<String> rgbLighting, List<String> brand) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        if(memoryType.isEmpty()){
            memoryType = memoryRepository.findDistinctMemoryType();
        }
        if(capacityGB.isEmpty()){
            capacityGB = memoryRepository.findDistinctCapacityGB();
        }
        if(speedMHz.isEmpty()){
            speedMHz = memoryRepository.findDistinctSpeedMHz();
        }
        if(formFactor.isEmpty()){
            formFactor = memoryRepository.findDistinctFormFactor();
        }
        if(rgbLighting.isEmpty()){
            rgbLighting = memoryRepository.findDistinctRgbLighting();
        }
        if(brand.isEmpty()){
            brand = memoryRepository.findDistinctBrand();
        }

        Page<Memory> memoryPage = memoryRepository.findByMemoryTypeInAndCapacityGBInAndSpeedMHzInAndFormFactorInAndRgbLightingInAndBrandIn(memoryType,capacityGB,speedMHz,formFactor,rgbLighting,brand,pageable);
        List<Memory> memoryList = memoryPage.getContent();

        List<MemoryResponseDTO> dtos = memoryList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<MemoryResponseDTO>(dtos,memoryPage.getTotalElements(),memoryPage.getTotalPages(),memoryPage.isLast());
    }

    @Override
    public MemoryResponseDTO add(MemoryRequestDTO dto) {
        Memory memory = new Memory();

        memory.setName(dto.getName());
        memory.setImgUrl(dto.getImgUrl());
        memory.setDescription(dto.getDescription());
        memory.setPrice(dto.getPrice());
        memory.setLatest(dto.isLatest());
        memory.setTop(dto.isTop());
        memory.setDeal(dto.getDeal());
        memory.setMemoryType(dto.getMemoryType());
        memory.setCapacityGB(dto.getCapacityGB());
        memory.setSpeedMHz(dto.getSpeedMHz());
        memory.setFormFactor(dto.getFormFactor());
        memory.setRgbLighting(dto.getRgbLighting());
        memory.setBrand(dto.getBrand());

        Memory newMemory =  memoryRepository.save(memory);

        inventoryClient.addQuantity(newMemory.getId(),dto.getQuantity());

        return convertToDTO(newMemory);
    }

    MemoryResponseDTO convertToDTO(Memory memory ){
        return new MemoryResponseDTO(
                memory.getId(),
                memory.getName(),
                memory.getImgUrl(),
                memory.getDescription(),
                memory.getPrice(),
                memory.isLatest(),
                memory.isTop(),
                memory.getDeal(),
                memory.getMemoryType(),
                memory.getCapacityGB(),
                memory.getSpeedMHz(),
                memory.getFormFactor(),
                memory.getRgbLighting(),
                memory.getBrand()
        );
    }

    @Override
    public List<MemoryResponseDTO> isNew() {
        List<Memory> memorys = memoryRepository.findAllByLatest(true);
        return memorys.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MemoryResponseDTO> isTop() {
        List<Memory> memorys = memoryRepository.findAllByTop(true);
        return memorys.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MemoryResponseDTO> isDeal() {
        List<Memory> memorys = memoryRepository.findAllByDealNot(0);
        return memorys.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<MemoryResponseDTO> search(String text) {
        List<Memory> memorys = memoryRepository.findByNameContainingIgnoreCase(text);
        return memorys.stream().map(this::convertToDTO).toList();
    }

    @Override
    public MemoryResponseDTO getProduct(UUID id) {
        Memory memory = memoryRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));
        return convertToDTO(memory);
    }

    @Override
    public MemoryResponseDTO updateProduct(UUID id, MemoryRequestDTO dto) {
        Memory memory = memoryRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));

        memory.setName(dto.getName());
        memory.setImgUrl(dto.getImgUrl());
        memory.setDescription(dto.getDescription());
        memory.setPrice(dto.getPrice());
        memory.setLatest(dto.isLatest());
        memory.setTop(dto.isTop());
        memory.setDeal(dto.getDeal());
        memory.setMemoryType(dto.getMemoryType());
        memory.setCapacityGB(dto.getCapacityGB());
        memory.setSpeedMHz(dto.getSpeedMHz());
        memory.setFormFactor(dto.getFormFactor());
        memory.setRgbLighting(dto.getRgbLighting());
        memory.setBrand(dto.getBrand());

        Memory newMemory =  memoryRepository.save(memory);

        inventoryClient.updateQuantity(newMemory.getId(),dto.getQuantity(), newMemory.getName());

        return convertToDTO(newMemory);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(memoryRepository.existsById(id)){
            memoryRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new ProductNotFoundException("Product id:"+id+" is invalid");
        }
    }

}
