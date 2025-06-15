package com.tisitha.product_service.service;


import com.tisitha.product_service.dto.ProductPageSortDto;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardFilterOptionsDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardRequestDTO;
import com.tisitha.product_service.dto.graphicsCard.GraphicsCardResponseDTO;
import com.tisitha.product_service.feign.InventoryClient;
import com.tisitha.product_service.model.GraphicsCard;
import com.tisitha.product_service.repo.GraphicsCardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GraphicsCardServiceImp implements GraphicsCardService {

    private final GraphicsCardRepository graphicsCardRepository;
    private final InventoryClient inventoryClient;

    public GraphicsCardServiceImp(GraphicsCardRepository graphicsCardRepository, InventoryClient inventoryClient) {
        this.graphicsCardRepository = graphicsCardRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public GraphicsCardFilterOptionsDTO getAvailableFilters() {
        return new GraphicsCardFilterOptionsDTO(
                graphicsCardRepository.findDistinctGpuManufacturer(),
                graphicsCardRepository.findDistinctGpuSeries(),
                graphicsCardRepository.findDistinctVRamCapacity());
    }

    @Override
    public ProductPageSortDto<GraphicsCardResponseDTO> getAll(Integer pageNumber, Integer pageSize, String sortBy, String dir, List<String> gpuManufacturer, List<String> gpuSeries, List<String> vRamCapacity) {
        Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<GraphicsCard> graphicsCardPage = graphicsCardRepository.findAll(pageable);
        List<GraphicsCard> graphicsCardList1 = graphicsCardPage.getContent();

        if(gpuManufacturer.isEmpty()){
            gpuManufacturer = graphicsCardRepository.findDistinctGpuManufacturer();
        }
        if(gpuSeries.isEmpty()){
            gpuSeries = graphicsCardRepository.findDistinctGpuSeries();
        }
        if(vRamCapacity.isEmpty()){
            vRamCapacity = graphicsCardRepository.findDistinctVRamCapacity();
        }

        List<GraphicsCard> graphicsCardList2 = graphicsCardRepository.findByGpuManufacturerInAndGpuSeriesInAndVramGbIn(gpuManufacturer,gpuSeries,vRamCapacity);
        List<GraphicsCard> graphicsCardList = graphicsCardList1.stream().filter(graphicsCardList2::contains).toList();

        List<GraphicsCardResponseDTO> dtos = graphicsCardList.stream().map(this::convertToDTO).toList();

        return new ProductPageSortDto<GraphicsCardResponseDTO>(dtos,graphicsCardPage.getTotalElements(),graphicsCardPage.getTotalPages(),graphicsCardPage.isLast());
    }

    @Override
    public GraphicsCardResponseDTO add(GraphicsCardRequestDTO dto) {
        GraphicsCard graphicsCard = new GraphicsCard();

        graphicsCard.setName(dto.getName());
        graphicsCard.setImgUrl(dto.getImgUrl());
        graphicsCard.setDescription(dto.getDescription());
        graphicsCard.setPrice(dto.getPrice());
        graphicsCard.setNew(dto.isNew());
        graphicsCard.setTop(dto.isTop());
        graphicsCard.setDeal(dto.getDeal());
        graphicsCard.setGpuManufacturer(dto.getGpuManufacturer());
        graphicsCard.setGpuSeries(dto.getGpuSeries());
        graphicsCard.setVramGb(dto.getVramGb());

        GraphicsCard newGraphicsCard =  graphicsCardRepository.save(graphicsCard);

        inventoryClient.addQuantity(newGraphicsCard.getId(),dto.getQuantity());

        return convertToDTO(newGraphicsCard);
    }

    GraphicsCardResponseDTO convertToDTO(GraphicsCard graphicsCard){
        return new GraphicsCardResponseDTO(
                graphicsCard.getId(),
                graphicsCard.getName(),
                graphicsCard.getImgUrl(),
                graphicsCard.getDescription(),
                graphicsCard.getPrice(),
                graphicsCard.isNew(),
                graphicsCard.isTop(),
                graphicsCard.getDeal(),
                graphicsCard.getGpuManufacturer(),
                graphicsCard.getGpuSeries(),
                graphicsCard.getVramGb()
        );
    }

    @Override
    public List<GraphicsCardResponseDTO> isNew() {
        List<GraphicsCard> graphicsCards = graphicsCardRepository.findAllByIsNew(true);
        return graphicsCards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<GraphicsCardResponseDTO> isTop() {
        List<GraphicsCard> graphicsCards = graphicsCardRepository.findAllByIsTop(true);
        return graphicsCards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<GraphicsCardResponseDTO> isDeal() {
        List<GraphicsCard> graphicsCards = graphicsCardRepository.findAllByDealNot(0);
        return graphicsCards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<GraphicsCardResponseDTO> search(String text) {
        List<GraphicsCard> graphicsCards = graphicsCardRepository.findByNameContainingIgnoreCase(text);
        return graphicsCards.stream().map(this::convertToDTO).toList();
    }

    @Override
    public GraphicsCardResponseDTO getProduct(UUID id) {
        GraphicsCard graphicsCard = graphicsCardRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
        return convertToDTO(graphicsCard);
    }

    @Override
    public GraphicsCardResponseDTO updateProduct(UUID id, GraphicsCardRequestDTO dto) {
        GraphicsCard graphicsCard = graphicsCardRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));

        graphicsCard.setName(dto.getName());
        graphicsCard.setImgUrl(dto.getImgUrl());
        graphicsCard.setDescription(dto.getDescription());
        graphicsCard.setPrice(dto.getPrice());
        graphicsCard.setNew(dto.isNew());
        graphicsCard.setTop(dto.isTop());
        graphicsCard.setDeal(dto.getDeal());
        graphicsCard.setGpuManufacturer(dto.getGpuManufacturer());
        graphicsCard.setGpuSeries(dto.getGpuSeries());
        graphicsCard.setVramGb(dto.getVramGb());

        GraphicsCard newGraphicsCard =  graphicsCardRepository.save(graphicsCard);

        inventoryClient.updateQuantity(newGraphicsCard.getId(),dto.getQuantity());

        return convertToDTO(newGraphicsCard);
    }

    @Override
    public void deleteProduct(UUID id) {
        if(graphicsCardRepository.existsById(id)){
            graphicsCardRepository.deleteById(id);
            inventoryClient.deleteQuantity(id);
        }
        else {
            throw new RuntimeException("Invalid Product");
        }
    }

}
