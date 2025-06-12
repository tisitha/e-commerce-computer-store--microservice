package com.tisitha.product_service.service;

import com.tisitha.product_service.dto.CasingFilterOptionsDTO;
import com.tisitha.product_service.dto.CasingRequestDTO;
import com.tisitha.product_service.dto.CasingResponseDTO;
import com.tisitha.product_service.dto.ProductPageSortDto;
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

    public CasingServiceImp(CasingRepository casingRepository) {
        this.casingRepository = casingRepository;
    }

    public CasingFilterOptionsDTO getAvailableFilters() {
        return new CasingFilterOptionsDTO(
                casingRepository.findDistinctCaseType(),
                casingRepository.findDistinctMaxGPULength(),
                casingRepository.findDistinctIncludedFans());
    }

    @Override
    public ProductPageSortDto<CasingResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> caseType, List<String> maxGPULength, List<String> includedFans) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Casing> casingPage = casingRepository.findAll(pageable);
        List<Casing> casingList1 = casingPage.getContent();
        List<Casing> casingList2 = casingRepository.findByCaseTypeInAndMaxGPULengthInAndIncludedFansIn(caseType,maxGPULength,includedFans);
        casingList1.retainAll(casingList2);

        List<CasingResponseDTO> dtos = casingList1.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<CasingResponseDTO>(dtos,casingPage.getTotalElements(),casingPage.getTotalPages(),casingPage.isLast());
    }

    @Override
    public CasingResponseDTO add(CasingRequestDTO dto) {
        Casing casing = new Casing();

        casing.setName(dto.getName());
        casing.setImgUrl(dto.getImgUrl());
        casing.setDescription(dto.getDescription());
        casing.setPrice(dto.getPrice());
        casing.setNew(dto.isNew());
        casing.setTop(dto.isTop());
        casing.setDeal(dto.getDeal());
        casing.setCaseType(dto.getCaseType());
        casing.setMaxGPULength(dto.getMaxGPULength());
        casing.setIncludedFans(dto.getIncludedFans());

        Casing newCasing =  casingRepository.save(casing);

        //send Inventory

        return convertToDTO(newCasing);
    }

    CasingResponseDTO convertToDTO(Casing casing ){
        return new CasingResponseDTO(
                casing.getId(),
                casing.getName(),
                casing.getImgUrl(),
                casing.getDescription(),
                casing.getPrice(),
                casing.isNew(),
                casing.isTop(),
                casing.getDeal(),
                casing.getCaseType(),
                casing.getMaxGPULength(),
                casing.getIncludedFans()
        );
    }

    @Override
    public List<CasingResponseDTO> isNew() {
        List<Casing> casings = casingRepository.findAllByIsNew(true);
        return casings.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<CasingResponseDTO> isTop() {
        List<Casing> casings = casingRepository.findAllByIsTop(true);
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
        Casing casing = casingRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(casing);
    }

    @Override
    public CasingResponseDTO updateProduct(UUID id, CasingRequestDTO dto) {
        Casing casing = casingRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        casing.setName(dto.getName());
        casing.setImgUrl(dto.getImgUrl());
        casing.setDescription(dto.getDescription());
        casing.setPrice(dto.getPrice());
        casing.setNew(dto.isNew());
        casing.setTop(dto.isTop());
        casing.setDeal(dto.getDeal());
        casing.setCaseType(dto.getCaseType());
        casing.setMaxGPULength(dto.getMaxGPULength());
        casing.setIncludedFans(dto.getIncludedFans());

        Casing newCasing =  casingRepository.save(casing);

        //send Inventory

        return convertToDTO(newCasing);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(casingRepository.existsById(id)){
            casingRepository.deleteById(id);
            //delete from Inventory
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
