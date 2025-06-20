package com.tisitha.product_service.service;


import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.storage.StorageFilterOptionsDTO;
import com.tisitha.product_service.dto.storage.StorageRequestDTO;
import com.tisitha.product_service.dto.storage.StorageResponseDTO;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.Storage;
import com.tisitha.product_service.repo.StorageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StorageServiceImp implements StorageService{

    private final StorageRepository storageRepository;
    private final InventoryClient inventoryClient;

    public StorageServiceImp(StorageRepository storageRepository, InventoryClient inventoryClient) {
        this.storageRepository = storageRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public StorageFilterOptionsDTO getAvailableFilters() {
        return new StorageFilterOptionsDTO(
                storageRepository.findDistinctBrand(),
                storageRepository.findDistinctStorageType(),
                storageRepository.findDistinctCapacityGB(),
                storageRepository.findDistinctInterfaceType(),
                storageRepository.findDistinctUsageType());
    }

    @Override
    public ProductPageSortDto<StorageResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> storageType, List<String> capacityGB, List<String> interfaceType, List<String> usageType) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Storage> storagePage = storageRepository.findAll(pageable);
        List<Storage> storageList1 = storagePage.getContent();

        if(brand.isEmpty()){
            brand = storageRepository.findDistinctBrand();
        }
        if(storageType.isEmpty()){
            storageType = storageRepository.findDistinctStorageType();
        }
        if(capacityGB.isEmpty()){
            capacityGB = storageRepository.findDistinctCapacityGB();
        }
        if(interfaceType.isEmpty()){
            interfaceType = storageRepository.findDistinctInterfaceType();
        }
        if(usageType.isEmpty()){
            usageType = storageRepository.findDistinctUsageType();
        }

        List<Storage> storageList2 = storageRepository.findByBrandInAndStorageTypeInAndCapacityGBInAndInterfaceTypeInAndUsageTypeIn(brand,storageType,capacityGB,interfaceType,usageType);
        List<Storage> storageList = storageList1.stream().filter(storageList2::contains).toList();

        List<StorageResponseDTO> dtos = storageList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<StorageResponseDTO>(dtos,storagePage.getTotalElements(),storagePage.getTotalPages(),storagePage.isLast());
    }

    @Override
    public StorageResponseDTO add(StorageRequestDTO dto) {
        Storage storage = new Storage();

        storage.setName(dto.getName());
        storage.setImgUrl(dto.getImgUrl());
        storage.setDescription(dto.getDescription());
        storage.setPrice(dto.getPrice());
        storage.setLatest(dto.isLatest());
        storage.setTop(dto.isTop());
        storage.setDeal(dto.getDeal());
        storage.setBrand(dto.getBrand());
        storage.setStorageType(dto.getStorageType());
        storage.setCapacityGB(dto.getCapacityGB());
        storage.setInterfaceType(dto.getInterfaceType());
        storage.setUsageType(dto.getUsageType());

        Storage newStorage =  storageRepository.save(storage);

        inventoryClient.addQuantity(newStorage.getId(),dto.getQuantity());

        return convertToDTO(newStorage);
    }

    StorageResponseDTO convertToDTO(Storage storage ){
        return new StorageResponseDTO(
                storage.getId(),
                storage.getName(),
                storage.getImgUrl(),
                storage.getDescription(),
                storage.getPrice(),
                storage.isLatest(),
                storage.isTop(),
                storage.getDeal(),
                storage.getBrand(),
                storage.getStorageType(),
                storage.getCapacityGB(),
                storage.getInterfaceType(),
                storage.getUsageType()
        );
    }

    @Override
    public List<StorageResponseDTO> isNew() {
        List<Storage> storages = storageRepository.findAllByLatest(true);
        return storages.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<StorageResponseDTO> isTop() {
        List<Storage> storages = storageRepository.findAllByTop(true);
        return storages.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<StorageResponseDTO> isDeal() {
        List<Storage> storages = storageRepository.findAllByDealNot(0);
        return storages.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<StorageResponseDTO> search(String text) {
        List<Storage> storages = storageRepository.findByNameContainingIgnoreCase(text);
        return storages.stream().map(this::convertToDTO).toList();
    }

    @Override
    public StorageResponseDTO getProduct(UUID id) {
        Storage storage = storageRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(storage);
    }

    @Override
    public StorageResponseDTO updateProduct(UUID id, StorageRequestDTO dto) {
        Storage storage = storageRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        storage.setName(dto.getName());
        storage.setImgUrl(dto.getImgUrl());
        storage.setDescription(dto.getDescription());
        storage.setPrice(dto.getPrice());
        storage.setLatest(dto.isLatest());
        storage.setTop(dto.isTop());
        storage.setDeal(dto.getDeal());
        storage.setBrand(dto.getBrand());
        storage.setStorageType(dto.getStorageType());
        storage.setCapacityGB(dto.getCapacityGB());
        storage.setInterfaceType(dto.getInterfaceType());
        storage.setUsageType(dto.getUsageType());

        Storage newStorage =  storageRepository.save(storage);

        inventoryClient.updateQuantity(newStorage.getId(),dto.getQuantity());

        return convertToDTO(newStorage);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(storageRepository.existsById(id)){
            storageRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
