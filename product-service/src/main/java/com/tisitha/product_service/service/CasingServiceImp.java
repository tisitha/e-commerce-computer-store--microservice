package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.casing.CasingFilterOptionsDTO;
import com.tisitha.product_service.dto.casing.CasingRequestDTO;
import com.tisitha.product_service.dto.casing.CasingResponseDTO;
import com.tisitha.product_service.exception.ProductNotFoundException;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.Casing;
import com.tisitha.product_service.repo.CasingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CasingServiceImp implements CasingService{

    private final CasingRepository casingRepository;

    private final InventoryClient inventoryClient;

    public CasingServiceImp(CasingRepository casingRepository, InventoryClient inventoryClient) {
        this.casingRepository = casingRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public CasingFilterOptionsDTO getAvailableFilters() {
        return new CasingFilterOptionsDTO(
                casingRepository.findDistinctBrand(),
                casingRepository.findDistinctCaseType(),
                casingRepository.findDistinctMaxGPULength(),
                casingRepository.findDistinctIncludedFans());
    }

    @Override
    public ProductPageSortDto<CasingResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir,List<String> brand, List<String> caseType, List<String> maxGPULength, List<String> includedFans) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        if(brand.isEmpty()){
            brand = casingRepository.findDistinctBrand();
        }
        if(caseType.isEmpty()){
            caseType = casingRepository.findDistinctCaseType();
        }
        if(maxGPULength.isEmpty()){
            maxGPULength = casingRepository.findDistinctMaxGPULength();
        }
        if(includedFans.isEmpty()){
            includedFans = casingRepository.findDistinctIncludedFans();
        }
        Page<Casing> casingPage = casingRepository.findByBrandInAndCaseTypeInAndMaxGPULengthInAndIncludedFansIn(brand,caseType,maxGPULength,includedFans,pageable);
        List<Casing> casingList = casingPage.getContent();

        List<CasingResponseDTO> dtos = casingList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<CasingResponseDTO>(dtos,casingPage.getTotalElements(),casingPage.getTotalPages(),casingPage.isLast());
    }

    @Override
    public CasingResponseDTO add(CasingRequestDTO dto) {
        Casing casing = new Casing();

        casing.setName(dto.getName());
        casing.setImgUrl(dto.getImgUrl());
        casing.setDescription(dto.getDescription());
        casing.setPrice(dto.getPrice());
        casing.setLatest(dto.isLatest());
        casing.setTop(dto.isTop());
        casing.setDeal(dto.getDeal());
        casing.setBrand(dto.getBrand());
        casing.setCaseType(dto.getCaseType());
        casing.setMaxGPULength(dto.getMaxGPULength());
        casing.setIncludedFans(dto.getIncludedFans());

        Casing newCasing =  casingRepository.save(casing);

        inventoryClient.addQuantity(newCasing.getId(),dto.getQuantity());

        return convertToDTO(newCasing);
    }

    CasingResponseDTO convertToDTO(Casing casing ){
        return new CasingResponseDTO(
                casing.getId(),
                casing.getName(),
                casing.getImgUrl(),
                casing.getDescription(),
                casing.getPrice(),
                casing.isLatest(),
                casing.isTop(),
                casing.getDeal(),
                casing.getBrand(),
                casing.getCaseType(),
                casing.getMaxGPULength(),
                casing.getIncludedFans()
        );
    }

    @Override
    public List<CasingResponseDTO> isNew() {
        List<Casing> casings = casingRepository.findAllByLatest(true);
        return casings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<CasingResponseDTO> isTop() {
        List<Casing> casings = casingRepository.findAllByTop(true);
        return casings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<CasingResponseDTO> isDeal() {
        List<Casing> casings = casingRepository.findAllByDealNot(0);
        return casings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<CasingResponseDTO> search(String text) {
        List<Casing> casings = casingRepository.findByNameContainingIgnoreCase(text);
        return casings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public CasingResponseDTO getProduct(UUID id) {
        Casing casing = casingRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));
        return convertToDTO(casing);
    }

    @Override
    public CasingResponseDTO updateProduct(UUID id, CasingRequestDTO dto) {
        Casing casing = casingRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product id:"+id+" is invalid"));

        casing.setName(dto.getName());
        casing.setImgUrl(dto.getImgUrl());
        casing.setDescription(dto.getDescription());
        casing.setPrice(dto.getPrice());
        casing.setLatest(dto.isLatest());
        casing.setTop(dto.isTop());
        casing.setDeal(dto.getDeal());
        casing.setCaseType(dto.getCaseType());
        casing.setMaxGPULength(dto.getMaxGPULength());
        casing.setIncludedFans(dto.getIncludedFans());

        Casing newCasing =  casingRepository.save(casing);

        inventoryClient.updateQuantity(newCasing.getId(),dto.getQuantity(), newCasing.getName());

        return convertToDTO(newCasing);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(casingRepository.existsById(id)){
            casingRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new ProductNotFoundException("Product id:"+id+" is invalid");
        }
    }

}
