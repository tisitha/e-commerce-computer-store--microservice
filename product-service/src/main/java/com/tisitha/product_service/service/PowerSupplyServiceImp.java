package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyFilterOptionsDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyRequestDTO;
import com.tisitha.product_service.dto.powerSupply.PowerSupplyResponseDTO;
import com.tisitha.product_service.exception.ProductNotFoundException;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.PowerSupply;
import com.tisitha.product_service.repo.PowerSupplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PowerSupplyServiceImp implements PowerSupplyService{

    private final PowerSupplyRepository powerSupplyRepository;
    private final InventoryClient inventoryClient;

    public PowerSupplyServiceImp(PowerSupplyRepository powerSupplyRepository, InventoryClient inventoryClient) {
        this.powerSupplyRepository = powerSupplyRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public PowerSupplyFilterOptionsDTO getAvailableFilters() {
        return new PowerSupplyFilterOptionsDTO(
                powerSupplyRepository.findDistinctBrand(),
                powerSupplyRepository.findDistinctWattageOutput(),
                powerSupplyRepository.findDistinctCertificationRating(),
                powerSupplyRepository.findDistinctFormFactor(),
                powerSupplyRepository.findDistinctModularityType());
    }

    @Override
    public ProductPageSortDto<PowerSupplyResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> wattageOutput, List<String> certificationRating, List<String> formFactor, List<String> modularityType) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        if(brand.isEmpty()){
            brand = powerSupplyRepository.findDistinctBrand();
        }
        if(wattageOutput.isEmpty()){
            wattageOutput = powerSupplyRepository.findDistinctWattageOutput();
        }
        if(certificationRating.isEmpty()){
            certificationRating = powerSupplyRepository.findDistinctCertificationRating();
        }
        if(formFactor.isEmpty()){
            formFactor = powerSupplyRepository.findDistinctFormFactor();
        }
        if(modularityType.isEmpty()){
            modularityType = powerSupplyRepository.findDistinctModularityType();
        }

        Page<PowerSupply> powerSupplyPage = powerSupplyRepository.findByBrandInAndWattageOutputInAndCertificationRatingInAndFormFactorInAndModularityTypeIn(brand,wattageOutput,certificationRating,formFactor,modularityType,pageable);
        List<PowerSupply> powerSupplyList = powerSupplyPage.getContent();

        List<PowerSupplyResponseDTO> dtos = powerSupplyList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<PowerSupplyResponseDTO>(dtos,powerSupplyPage.getTotalElements(),powerSupplyPage.getTotalPages(),powerSupplyPage.isLast());
    }

    @Override
    public PowerSupplyResponseDTO add(PowerSupplyRequestDTO dto) {
        PowerSupply powerSupply = new PowerSupply();

        powerSupply.setName(dto.getName());
        powerSupply.setImgUrl(dto.getImgUrl());
        powerSupply.setDescription(dto.getDescription());
        powerSupply.setPrice(dto.getPrice());
        powerSupply.setLatest(dto.isLatest());
        powerSupply.setTop(dto.isTop());
        powerSupply.setDeal(dto.getDeal());
        powerSupply.setBrand(dto.getBrand());
        powerSupply.setWattageOutput(dto.getWattageOutput());
        powerSupply.setCertificationRating(dto.getCertificationRating());
        powerSupply.setFormFactor(dto.getFormFactor());
        powerSupply.setModularityType(dto.getModularityType());

        PowerSupply newPowerSupply =  powerSupplyRepository.save(powerSupply);

        inventoryClient.addQuantity(newPowerSupply.getId(),dto.getQuantity());

        return convertToDTO(newPowerSupply);
    }

    PowerSupplyResponseDTO convertToDTO(PowerSupply powerSupply ){
        return new PowerSupplyResponseDTO(
                powerSupply.getId(),
                powerSupply.getName(),
                powerSupply.getImgUrl(),
                powerSupply.getDescription(),
                powerSupply.getPrice(),
                powerSupply.isLatest(),
                powerSupply.isTop(),
                powerSupply.getDeal(),
                powerSupply.getCategory(),
                powerSupply.getBrand(),
                powerSupply.getWattageOutput(),
                powerSupply.getCertificationRating(),
                powerSupply.getFormFactor(),
                powerSupply.getModularityType(),
                Objects.requireNonNull(inventoryClient.getQuantity(powerSupply.getId()).getBody()).getQuantity()
        );
    }

    @Override
    public List<PowerSupplyResponseDTO> isNew() {
        List<PowerSupply> powerSupplys = powerSupplyRepository.findAllByLatest(true);
        return powerSupplys.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<PowerSupplyResponseDTO> isTop() {
        List<PowerSupply> powerSupplys = powerSupplyRepository.findAllByTop(true);
        return powerSupplys.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<PowerSupplyResponseDTO> isDeal() {
        List<PowerSupply> powerSupplys = powerSupplyRepository.findAllByDealNot(0);
        return powerSupplys.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<PowerSupplyResponseDTO> search(String text) {
        List<PowerSupply> powerSupplys = powerSupplyRepository.findByNameContainingIgnoreCase(text);
        return powerSupplys.stream().map(this::convertToDTO).toList();
    }

    @Override
    public PowerSupplyResponseDTO getProduct(UUID id) {
        PowerSupply powerSupply = powerSupplyRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));
        return convertToDTO(powerSupply);
    }

    @Override
    public PowerSupplyResponseDTO updateProduct(UUID id, PowerSupplyRequestDTO dto) {
        PowerSupply powerSupply = powerSupplyRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));

        powerSupply.setName(dto.getName());
        powerSupply.setImgUrl(dto.getImgUrl());
        powerSupply.setDescription(dto.getDescription());
        powerSupply.setPrice(dto.getPrice());
        powerSupply.setLatest(dto.isLatest());
        powerSupply.setTop(dto.isTop());
        powerSupply.setDeal(dto.getDeal());
        powerSupply.setBrand(dto.getBrand());
        powerSupply.setWattageOutput(dto.getWattageOutput());
        powerSupply.setCertificationRating(dto.getCertificationRating());
        powerSupply.setFormFactor(dto.getFormFactor());
        powerSupply.setModularityType(dto.getModularityType());

        PowerSupply newPowerSupply =  powerSupplyRepository.save(powerSupply);

        inventoryClient.updateQuantity(newPowerSupply.getId(),dto.getQuantity(), newPowerSupply.getName());

        return convertToDTO(newPowerSupply);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(powerSupplyRepository.existsById(id)){
            powerSupplyRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new ProductNotFoundException("Product id:"+id+" is invalid");
        }
    }

}
