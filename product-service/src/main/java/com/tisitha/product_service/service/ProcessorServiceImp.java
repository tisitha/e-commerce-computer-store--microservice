package com.tisitha.product_service.service;


import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.processor.ProcessorFilterOptionsDTO;
import com.tisitha.product_service.dto.processor.ProcessorRequestDTO;
import com.tisitha.product_service.dto.processor.ProcessorResponseDTO;
import com.tisitha.product_service.model.Processor;
import com.tisitha.product_service.repo.ProcessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProcessorServiceImp implements ProcessorService{

    private final ProcessorRepository processorRepository;

    public ProcessorServiceImp(ProcessorRepository processorRepository) {
        this.processorRepository = processorRepository;
    }

    @Override
    public ProcessorFilterOptionsDTO getAvailableFilters() {
        return new ProcessorFilterOptionsDTO(
                processorRepository.findDistinctBrand(),
                processorRepository.findDistinctCpuSeries(),
                processorRepository.findDistinctCpuSocket(),
                processorRepository.findDistinctCoreCount(),
                processorRepository.findDistinctThreadCount(),
                processorRepository.findDistinctBaseClockSpeedGHz(),
                processorRepository.findDistinctIntegratedGraphics());
    }

    @Override
    public ProductPageSortDto<ProcessorResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> brand, List<String> cpuSeries, List<String> cpuSocket, List<String> coreCount, List<String> threadCount, List<String> baseClockSpeedGHz, List<String> integratedGraphics) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Processor> processorPage = processorRepository.findAll(pageable);
        List<Processor> processorList1 = processorPage.getContent();

        if(brand.isEmpty()){
            brand = processorRepository.findDistinctBrand();
        }
        if(cpuSeries.isEmpty()){
            cpuSeries = processorRepository.findDistinctCpuSeries();
        }
        if(cpuSocket.isEmpty()){
            cpuSocket = processorRepository.findDistinctCpuSocket();
        }
        if(coreCount.isEmpty()){
            coreCount = processorRepository.findDistinctCoreCount();
        }
        if(threadCount.isEmpty()){
            threadCount = processorRepository.findDistinctThreadCount();
        }
        if(baseClockSpeedGHz.isEmpty()){
            baseClockSpeedGHz = processorRepository.findDistinctBaseClockSpeedGHz();
        }
        if(integratedGraphics.isEmpty()){
            integratedGraphics = processorRepository.findDistinctIntegratedGraphics();
        }

        List<Processor> processorList2 = processorRepository.findByBrandInAndCpuSeriesInAndCpuSocketInAndCoreCountInAndThreadCountInAndBaseClockSpeedGHzInAndIntegratedGraphicsIn(brand,cpuSeries,cpuSocket,coreCount,threadCount,baseClockSpeedGHz,integratedGraphics);
        List<Processor> processorList = processorList1.stream().filter(processorList2::contains).toList();

        List<ProcessorResponseDTO> dtos = processorList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<ProcessorResponseDTO>(dtos,processorPage.getTotalElements(),processorPage.getTotalPages(),processorPage.isLast());
    }

    @Override
    public ProcessorResponseDTO add(ProcessorRequestDTO dto) {
        Processor processor = new Processor();

        processor.setName(dto.getName());
        processor.setImgUrl(dto.getImgUrl());
        processor.setDescription(dto.getDescription());
        processor.setPrice(dto.getPrice());
        processor.setNew(dto.isNew());
        processor.setTop(dto.isTop());
        processor.setDeal(dto.getDeal());
        processor.setBrand(dto.getBrand());
        processor.setCpuSeries(dto.getCpuSeries());
        processor.setCpuSocket(dto.getCpuSocket());
        processor.setCoreCount(dto.getCoreCount());
        processor.setThreadCount(dto.getThreadCount());
        processor.setBaseClockSpeedGHz(dto.getBaseClockSpeedGHz());
        processor.setIntegratedGraphics(dto.getIntegratedGraphics());

        Processor newProcessor =  processorRepository.save(processor);

        //send Inventory

        return convertToDTO(newProcessor);
    }

    ProcessorResponseDTO convertToDTO(Processor processor ){
        return new ProcessorResponseDTO(
                processor.getId(),
                processor.getName(),
                processor.getImgUrl(),
                processor.getDescription(),
                processor.getPrice(),
                processor.isNew(),
                processor.isTop(),
                processor.getDeal(),
                processor.getBrand(),
                processor.getCpuSeries(),
                processor.getCpuSocket(),
                processor.getCoreCount(),
                processor.getThreadCount(),
                processor.getBaseClockSpeedGHz(),
                processor.getIntegratedGraphics()
        );
    }

    @Override
    public List<ProcessorResponseDTO> isNew() {
        List<Processor> processors = processorRepository.findAllByIsNew(true);
        return processors.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<ProcessorResponseDTO> isTop() {
        List<Processor> processors = processorRepository.findAllByIsTop(true);
        return processors.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<ProcessorResponseDTO> isDeal() {
        List<Processor> processors = processorRepository.findAllByDealNot(0);
        return processors.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<ProcessorResponseDTO> search(String text) {
        List<Processor> processors = processorRepository.findByNameContainingIgnoreCase(text);
        return processors.stream().map(this::convertToDTO).toList();
    }

    @Override
    public ProcessorResponseDTO getProduct(UUID id) {
        Processor processor = processorRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(processor);
    }

    @Override
    public ProcessorResponseDTO updateProduct(UUID id, ProcessorRequestDTO dto) {
        Processor processor = processorRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        processor.setName(dto.getName());
        processor.setImgUrl(dto.getImgUrl());
        processor.setDescription(dto.getDescription());
        processor.setPrice(dto.getPrice());
        processor.setNew(dto.isNew());
        processor.setTop(dto.isTop());
        processor.setDeal(dto.getDeal());
        processor.setBrand(dto.getBrand());
        processor.setCpuSeries(dto.getCpuSeries());
        processor.setCpuSocket(dto.getCpuSocket());
        processor.setCoreCount(dto.getCoreCount());
        processor.setThreadCount(dto.getThreadCount());
        processor.setBaseClockSpeedGHz(dto.getBaseClockSpeedGHz());
        processor.setIntegratedGraphics(dto.getIntegratedGraphics());

        Processor newProcessor =  processorRepository.save(processor);

        //send Inventory

        return convertToDTO(newProcessor);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(processorRepository.existsById(id)){
            processorRepository.deleteById(id);
            //delete from Inventory
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
