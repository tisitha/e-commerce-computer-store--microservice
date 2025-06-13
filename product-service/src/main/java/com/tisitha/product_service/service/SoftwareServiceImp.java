package com.tisitha.product_service.service;


import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.software.SoftwareFilterOptionsDTO;
import com.tisitha.product_service.dto.software.SoftwareRequestDTO;
import com.tisitha.product_service.dto.software.SoftwareResponseDTO;
import com.tisitha.product_service.model.Software;
import com.tisitha.product_service.repo.SoftwareRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SoftwareServiceImp implements SoftwareService{

    private final SoftwareRepository softwareRepository;

    public SoftwareServiceImp(SoftwareRepository softwareRepository) {
        this.softwareRepository = softwareRepository;
    }

    @Override
    public SoftwareFilterOptionsDTO getAvailableFilters() {
        return new SoftwareFilterOptionsDTO(
                softwareRepository.findDistinctBrand(),
                softwareRepository.findDistinctYears(),
                softwareRepository.findDistinctUses());
    }

    @Override
    public ProductPageSortDto<SoftwareResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> years, List<String> uses) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Software> softwarePage = softwareRepository.findAll(pageable);
        List<Software> softwareList1 = softwarePage.getContent();

        if(brand.isEmpty()){
            brand = softwareRepository.findDistinctBrand();
        }
        if(years.isEmpty()){
            years = softwareRepository.findDistinctYears();
        }
        if(uses.isEmpty()){
            uses = softwareRepository.findDistinctUses();
        }

        List<Software> softwareList2 = softwareRepository.findByBrandInAndYearsInAndUsesIn(brand,years,uses);
        List<Software> softwareList = softwareList1.stream().filter(softwareList2::contains).toList();

        List<SoftwareResponseDTO> dtos = softwareList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<SoftwareResponseDTO>(dtos,softwarePage.getTotalElements(),softwarePage.getTotalPages(),softwarePage.isLast());
    }

    @Override
    public SoftwareResponseDTO add(SoftwareRequestDTO dto) {
        Software software = new Software();

        software.setName(dto.getName());
        software.setImgUrl(dto.getImgUrl());
        software.setDescription(dto.getDescription());
        software.setPrice(dto.getPrice());
        software.setNew(dto.isNew());
        software.setTop(dto.isTop());
        software.setDeal(dto.getDeal());
        software.setBrand(dto.getBrand());
        software.setYears(dto.getYears());
        software.setUses(dto.getUses());

        Software newSoftware =  softwareRepository.save(software);

        //send Inventory

        return convertToDTO(newSoftware);
    }

    SoftwareResponseDTO convertToDTO(Software software ){
        return new SoftwareResponseDTO(
                software.getId(),
                software.getName(),
                software.getImgUrl(),
                software.getDescription(),
                software.getPrice(),
                software.isNew(),
                software.isTop(),
                software.getDeal(),
                software.getBrand(),
                software.getYears(),
                software.getUses()
        );
    }

    @Override
    public List<SoftwareResponseDTO> isNew() {
        List<Software> softwares = softwareRepository.findAllByIsNew(true);
        return softwares.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<SoftwareResponseDTO> isTop() {
        List<Software> softwares = softwareRepository.findAllByIsTop(true);
        return softwares.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<SoftwareResponseDTO> isDeal() {
        List<Software> softwares = softwareRepository.findAllByDealNot(0);
        return softwares.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<SoftwareResponseDTO> search(String text) {
        List<Software> softwares = softwareRepository.findByNameContainingIgnoreCase(text);
        return softwares.stream().map(this::convertToDTO).toList();
    }

    @Override
    public SoftwareResponseDTO getProduct(UUID id) {
        Software software = softwareRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(software);
    }

    @Override
    public SoftwareResponseDTO updateProduct(UUID id, SoftwareRequestDTO dto) {
        Software software = softwareRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        software.setName(dto.getName());
        software.setImgUrl(dto.getImgUrl());
        software.setDescription(dto.getDescription());
        software.setPrice(dto.getPrice());
        software.setNew(dto.isNew());
        software.setTop(dto.isTop());
        software.setDeal(dto.getDeal());
        software.setBrand(dto.getBrand());
        software.setYears(dto.getYears());
        software.setUses(dto.getUses());

        Software newSoftware =  softwareRepository.save(software);

        //send Inventory

        return convertToDTO(newSoftware);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(softwareRepository.existsById(id)){
            softwareRepository.deleteById(id);
            //delete from Inventory
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
