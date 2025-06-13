package com.tisitha.product_service.service;


import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.graphicCard.GraphicCardFilterOptionsDTO;
import com.tisitha.product_service.dto.graphicCard.GraphicCardRequestDTO;
import com.tisitha.product_service.dto.graphicCard.GraphicCardResponseDTO;
import com.tisitha.product_service.model.GraphicCard;
import com.tisitha.product_service.repo.GraphicCardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GraphicCardServiceImp implements GraphicCardService{

    private final GraphicCardRepository graphicCardRepository;

    public GraphicCardServiceImp(GraphicCardRepository graphicCardRepository) {
        this.graphicCardRepository = graphicCardRepository;
    }

    @Override
    public GraphicCardFilterOptionsDTO getAvailableFilters() {
        return new GraphicCardFilterOptionsDTO(
                graphicCardRepository.findDistinctGpuManufacturer(),
                graphicCardRepository.findDistinctGpuSeries(),
                graphicCardRepository.findDistinctVRamCapacity());
    }

    @Override
    public ProductPageSortDto<GraphicCardResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> gpuManufacturer, List<String> gpuSeries, List<String> vRamCapacity) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<GraphicCard> graphicCardPage = graphicCardRepository.findAll(pageable);
        List<GraphicCard> graphicCardList1 = graphicCardPage.getContent();

        if(gpuManufacturer.isEmpty()){
            gpuManufacturer = graphicCardRepository.findDistinctGpuManufacturer();
        }
        if(gpuSeries.isEmpty()){
            gpuSeries = graphicCardRepository.findDistinctGpuSeries();
        }
        if(vRamCapacity.isEmpty()){
            vRamCapacity = graphicCardRepository.findDistinctVRamCapacity();
        }

        List<GraphicCard> graphicCardList2 = graphicCardRepository.findByGpuManufacturerInAndGpuSeriesInAndVRamCapacityIn(gpuManufacturer,gpuSeries,vRamCapacity);
        List<GraphicCard> graphicCardList = graphicCardList1.stream().filter(graphicCardList2::contains).toList();

        List<GraphicCardResponseDTO> dtos = graphicCardList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<GraphicCardResponseDTO>(dtos,graphicCardPage.getTotalElements(),graphicCardPage.getTotalPages(),graphicCardPage.isLast());
    }

    @Override
    public GraphicCardResponseDTO add(GraphicCardRequestDTO dto) {
        GraphicCard graphicCard = new GraphicCard();

        graphicCard.setName(dto.getName());
        graphicCard.setImgUrl(dto.getImgUrl());
        graphicCard.setDescription(dto.getDescription());
        graphicCard.setPrice(dto.getPrice());
        graphicCard.setNew(dto.isNew());
        graphicCard.setTop(dto.isTop());
        graphicCard.setDeal(dto.getDeal());
        graphicCard.setGpuManufacturer(dto.getGpuManufacturer());
        graphicCard.setGpuSeries(dto.getGpuSeries());
        graphicCard.setVRamCapacity(dto.getVRamCapacity());

        GraphicCard newGraphicCard =  graphicCardRepository.save(graphicCard);

        //send Inventory

        return convertToDTO(newGraphicCard);
    }

    GraphicCardResponseDTO convertToDTO(GraphicCard graphicCard ){
        return new GraphicCardResponseDTO(
                graphicCard.getId(),
                graphicCard.getName(),
                graphicCard.getImgUrl(),
                graphicCard.getDescription(),
                graphicCard.getPrice(),
                graphicCard.isNew(),
                graphicCard.isTop(),
                graphicCard.getDeal(),
                graphicCard.getGpuManufacturer(),
                graphicCard.getGpuSeries(),
                graphicCard.getVRamCapacity()
        );
    }

    @Override
    public List<GraphicCardResponseDTO> isNew() {
        List<GraphicCard> graphicCards = graphicCardRepository.findAllByIsNew(true);
        return graphicCards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<GraphicCardResponseDTO> isTop() {
        List<GraphicCard> graphicCards = graphicCardRepository.findAllByIsTop(true);
        return graphicCards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<GraphicCardResponseDTO> isDeal() {
        List<GraphicCard> graphicCards = graphicCardRepository.findAllByDealNot(0);
        return graphicCards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<GraphicCardResponseDTO> search(String text) {
        List<GraphicCard> graphicCards = graphicCardRepository.findByNameContainingIgnoreCase(text);
        return graphicCards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public GraphicCardResponseDTO getProduct(UUID id) {
        GraphicCard graphicCard = graphicCardRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(graphicCard);
    }

    @Override
    public GraphicCardResponseDTO updateProduct(UUID id, GraphicCardRequestDTO dto) {
        GraphicCard graphicCard = graphicCardRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        graphicCard.setName(dto.getName());
        graphicCard.setImgUrl(dto.getImgUrl());
        graphicCard.setDescription(dto.getDescription());
        graphicCard.setPrice(dto.getPrice());
        graphicCard.setNew(dto.isNew());
        graphicCard.setTop(dto.isTop());
        graphicCard.setDeal(dto.getDeal());
        graphicCard.setGpuManufacturer(dto.getGpuManufacturer());
        graphicCard.setGpuSeries(dto.getGpuSeries());
        graphicCard.setVRamCapacity(dto.getVRamCapacity());

        GraphicCard newGraphicCard =  graphicCardRepository.save(graphicCard);

        //send Inventory

        return convertToDTO(newGraphicCard);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(graphicCardRepository.existsById(id)){
            graphicCardRepository.deleteById(id);
            //delete from Inventory
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
