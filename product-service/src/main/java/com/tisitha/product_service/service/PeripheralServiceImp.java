package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.peripheral.PeripheralFilterOptionsDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralRequestDTO;
import com.tisitha.product_service.dto.peripheral.PeripheralResponseDTO;
import com.tisitha.product_service.exception.ProductNotFoundException;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.Peripheral;
import com.tisitha.product_service.repo.PeripheralRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PeripheralServiceImp implements PeripheralService{

    private final PeripheralRepository peripheralRepository;
    private final InventoryClient inventoryClient;

    public PeripheralServiceImp(PeripheralRepository peripheralRepository, InventoryClient inventoryClient) {
        this.peripheralRepository = peripheralRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public PeripheralFilterOptionsDTO getAvailableFilters() {
        return new PeripheralFilterOptionsDTO(
                peripheralRepository.findDistinctBrand(),
                peripheralRepository.findDistinctPeripheralType(),
                peripheralRepository.findDistinctConnectivityType(),
                peripheralRepository.findDistinctRgbLighting());
    }

    @Override
    public ProductPageSortDto<PeripheralResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> peripheralType, List<String> connectivityType, List<String> rgbLighting) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        if(brand.isEmpty()){
            brand = peripheralRepository.findDistinctBrand();
        }
        if(peripheralType.isEmpty()){
            peripheralType = peripheralRepository.findDistinctPeripheralType();
        }
        if(connectivityType.isEmpty()){
            connectivityType = peripheralRepository.findDistinctConnectivityType();
        }
        if(rgbLighting.isEmpty()){
            rgbLighting = peripheralRepository.findDistinctRgbLighting();
        }

        Page<Peripheral> peripheralPage = peripheralRepository.findByBrandInAndPeripheralTypeInAndConnectivityTypeInAndRgbLightingIn(brand,peripheralType,connectivityType,rgbLighting,pageable);
        List<Peripheral> peripheralList = peripheralPage.getContent();

        List<PeripheralResponseDTO> dtos = peripheralList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<PeripheralResponseDTO>(dtos,peripheralPage.getTotalElements(),peripheralPage.getTotalPages(),peripheralPage.isLast());
    }

    @Override
    public PeripheralResponseDTO add(PeripheralRequestDTO dto) {
        Peripheral peripheral = new Peripheral();

        peripheral.setName(dto.getName());
        peripheral.setImgUrl(dto.getImgUrl());
        peripheral.setDescription(dto.getDescription());
        peripheral.setPrice(dto.getPrice());
        peripheral.setLatest(dto.isLatest());
        peripheral.setTop(dto.isTop());
        peripheral.setDeal(dto.getDeal());
        peripheral.setBrand(dto.getBrand());
        peripheral.setPeripheralType(dto.getPeripheralType());
        peripheral.setConnectivityType(dto.getConnectivityType());
        peripheral.setRgbLighting(dto.getRgbLighting());

        Peripheral newPeripheral =  peripheralRepository.save(peripheral);

        inventoryClient.addQuantity(newPeripheral.getId(),dto.getQuantity());

        return convertToDTO(newPeripheral);
    }

    PeripheralResponseDTO convertToDTO(Peripheral peripheral ){
        return new PeripheralResponseDTO(
                peripheral.getId(),
                peripheral.getName(),
                peripheral.getImgUrl(),
                peripheral.getDescription(),
                peripheral.getPrice(),
                peripheral.isLatest(),
                peripheral.isTop(),
                peripheral.getDeal(),
                peripheral.getBrand(),
                peripheral.getPeripheralType(),
                peripheral.getConnectivityType(),
                peripheral.getRgbLighting(),
                Objects.requireNonNull(inventoryClient.getQuantity(peripheral.getId()).getBody()).getQuantity()
        );
    }

    @Override
    public List<PeripheralResponseDTO> isNew() {
        List<Peripheral> peripherals = peripheralRepository.findAllByLatest(true);
        return peripherals.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<PeripheralResponseDTO> isTop() {
        List<Peripheral> peripherals = peripheralRepository.findAllByTop(true);
        return peripherals.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<PeripheralResponseDTO> isDeal() {
        List<Peripheral> peripherals = peripheralRepository.findAllByDealNot(0);
        return peripherals.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<PeripheralResponseDTO> search(String text) {
        List<Peripheral> peripherals = peripheralRepository.findByNameContainingIgnoreCase(text);
        return peripherals.stream().map(this::convertToDTO).toList();
    }

    @Override
    public PeripheralResponseDTO getProduct(UUID id) {
        Peripheral peripheral = peripheralRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));
        return convertToDTO(peripheral);
    }

    @Override
    public PeripheralResponseDTO updateProduct(UUID id, PeripheralRequestDTO dto) {
        Peripheral peripheral = peripheralRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));

        peripheral.setName(dto.getName());
        peripheral.setImgUrl(dto.getImgUrl());
        peripheral.setDescription(dto.getDescription());
        peripheral.setPrice(dto.getPrice());
        peripheral.setLatest(dto.isLatest());
        peripheral.setTop(dto.isTop());
        peripheral.setDeal(dto.getDeal());
        peripheral.setBrand(dto.getBrand());
        peripheral.setPeripheralType(dto.getPeripheralType());
        peripheral.setConnectivityType(dto.getConnectivityType());
        peripheral.setRgbLighting(dto.getRgbLighting());

        Peripheral newPeripheral =  peripheralRepository.save(peripheral);

        inventoryClient.updateQuantity(newPeripheral.getId(),dto.getQuantity(), newPeripheral.getName());

        return convertToDTO(newPeripheral);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(peripheralRepository.existsById(id)){
            peripheralRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new ProductNotFoundException("Product id:"+id+" is invalid");
        }
    }

}
