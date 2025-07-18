package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.cooling.CoolingFilterOptionsDTO;
import com.tisitha.product_service.dto.cooling.CoolingRequestDTO;
import com.tisitha.product_service.dto.cooling.CoolingResponseDTO;
import com.tisitha.product_service.exception.ProductNotFoundException;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.Cooling;
import com.tisitha.product_service.repo.CoolingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CoolingServiceImp implements CoolingService{

    private final CoolingRepository coolingRepository;
    private final InventoryClient inventoryClient;

    public CoolingServiceImp(CoolingRepository coolingRepository, InventoryClient inventoryClient) {
        this.coolingRepository = coolingRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public CoolingFilterOptionsDTO getAvailableFilters() {
        return new CoolingFilterOptionsDTO(
                coolingRepository.findDistinctBrand(),
                coolingRepository.findDistinctCoolingType(),
                coolingRepository.findDistinctSocketCompatibility(),
                coolingRepository.findDistinctFanSize(),
                coolingRepository.findDistinctRgbLighting());
    }

    @Override
    public ProductPageSortDto<CoolingResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> coolingType, List<String> socketCompatibility, List<String> fanSize, List<String> rgbLighting) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        if(brand.isEmpty()){
            brand = coolingRepository.findDistinctBrand();
        }
        if(coolingType.isEmpty()){
            coolingType = coolingRepository.findDistinctCoolingType();
        }
        if(socketCompatibility.isEmpty()){
            socketCompatibility = coolingRepository.findDistinctSocketCompatibility();
        }
        if(fanSize.isEmpty()){
            fanSize = coolingRepository.findDistinctFanSize();
        }
        if(rgbLighting.isEmpty()){
            rgbLighting = coolingRepository.findDistinctRgbLighting();
        }

        Page<Cooling> coolingPage =  coolingRepository.findByBrandInAndCoolingTypeInAndSocketCompatibilityInAndFanSizeInAndRgbLightingIn(brand,coolingType,socketCompatibility,fanSize,rgbLighting,pageable);
        List<Cooling> coolingList = coolingPage.getContent();

        List<CoolingResponseDTO> dtos = coolingList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<CoolingResponseDTO>(dtos,coolingPage.getTotalElements(),coolingPage.getTotalPages(),coolingPage.isLast());
    }

    @Override
    public CoolingResponseDTO add(CoolingRequestDTO dto) {
        Cooling cooling = new Cooling();

        cooling.setName(dto.getName());
        cooling.setImgUrl(dto.getImgUrl());
        cooling.setDescription(dto.getDescription());
        cooling.setPrice(dto.getPrice());
        cooling.setLatest(dto.isLatest());
        cooling.setTop(dto.isTop());
        cooling.setDeal(dto.getDeal());
        cooling.setBrand(dto.getBrand());
        cooling.setCoolingType(dto.getCoolingType());
        cooling.setSocketCompatibility(dto.getSocketCompatibility());
        cooling.setFanSize(dto.getFanSize());
        cooling.setRgbLighting(dto.getRgbLighting());

        Cooling newCooling =  coolingRepository.save(cooling);

        inventoryClient.addQuantity(newCooling.getId(),dto.getQuantity());

        return convertToDTO(newCooling);
    }

    CoolingResponseDTO convertToDTO(Cooling cooling ){
        return new CoolingResponseDTO(
                cooling.getId(),
                cooling.getName(),
                cooling.getImgUrl(),
                cooling.getDescription(),
                cooling.getPrice(),
                cooling.isLatest(),
                cooling.isTop(),
                cooling.getDeal(),
                cooling.getCategory(),
                cooling.getBrand(),
                cooling.getCoolingType(),
                cooling.getSocketCompatibility(),
                cooling.getFanSize(),
                cooling.getRgbLighting(),
                Objects.requireNonNull(inventoryClient.getQuantity(cooling.getId()).getBody()).getQuantity()
        );
    }

    @Override
    public List<CoolingResponseDTO> isNew() {
        List<Cooling> coolings = coolingRepository.findAllByLatest(true);
        return coolings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<CoolingResponseDTO> isTop() {
        List<Cooling> coolings = coolingRepository.findAllByTop(true);
        return coolings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<CoolingResponseDTO> isDeal() {
        List<Cooling> coolings = coolingRepository.findAllByDealNot(0);
        return coolings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<CoolingResponseDTO> search(String text) {
        List<Cooling> coolings = coolingRepository.findByNameContainingIgnoreCase(text);
        return coolings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public CoolingResponseDTO getProduct(UUID id) {
        Cooling cooling = coolingRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));
        return convertToDTO(cooling);
    }

    @Override
    public CoolingResponseDTO updateProduct(UUID id, CoolingRequestDTO dto) {
        Cooling cooling = coolingRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));

        cooling.setName(dto.getName());
        cooling.setImgUrl(dto.getImgUrl());
        cooling.setDescription(dto.getDescription());
        cooling.setPrice(dto.getPrice());
        cooling.setLatest(dto.isLatest());
        cooling.setTop(dto.isTop());
        cooling.setDeal(dto.getDeal());
        cooling.setBrand(dto.getBrand());
        cooling.setCoolingType(dto.getCoolingType());
        cooling.setSocketCompatibility(dto.getSocketCompatibility());
        cooling.setFanSize(dto.getFanSize());
        cooling.setRgbLighting(dto.getRgbLighting());

        Cooling newCooling =  coolingRepository.save(cooling);

        inventoryClient.updateQuantity(newCooling.getId(),dto.getQuantity(), newCooling.getName());

        return convertToDTO(newCooling);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(coolingRepository.existsById(id)){
            coolingRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new ProductNotFoundException("Product id:"+id+" is invalid");
        }
    }

}
