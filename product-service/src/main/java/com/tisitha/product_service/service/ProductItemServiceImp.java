package com.tisitha.product_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImp implements ProductItemService {

    private final CasingService casingService;
    private final CoolingService coolingService;
    private final DesktopService desktopService;
    private final GraphicsCardService graphicsCardService;
    private final LaptopService laptopService;
    private final MemoryService memoryService;
    private final MonitorService monitorService;
    private final MotherBoardService motherBoardService;
    private final PeripheralService peripheralService;
    private final PowerSupplyService powerSupplyService;
    private final ProcessorService processorService;
    private final SoftwareService softwareService;
    private final StorageService storageService;

    public List<Object> getNew(Integer size){
        List<Object> dto = new ArrayList<>();
        dto.addAll(casingService.isNew());
        dto.addAll(coolingService.isNew());
        dto.addAll(desktopService.isNew());
        dto.addAll(graphicsCardService.isNew());
        dto.addAll(laptopService.isNew());
        dto.addAll(memoryService.isNew());
        dto.addAll(monitorService.isNew());
        dto.addAll(motherBoardService.isNew());
        dto.addAll(peripheralService.isNew());
        dto.addAll(powerSupplyService.isNew());
        dto.addAll(processorService.isNew());
        dto.addAll(softwareService.isNew());
        dto.addAll(storageService.isNew());
        Collections.shuffle(dto);
        if(dto.size()>size){
            return dto.subList(0,size);
        }
        return dto;
    }

    public List<Object> getTop(Integer size){
        List<Object> dto = new ArrayList<>();
        dto.addAll(casingService.isTop());
        dto.addAll(coolingService.isTop());
        dto.addAll(desktopService.isTop());
        dto.addAll(graphicsCardService.isTop());
        dto.addAll(laptopService.isTop());
        dto.addAll(memoryService.isTop());
        dto.addAll(monitorService.isTop());
        dto.addAll(motherBoardService.isTop());
        dto.addAll(peripheralService.isTop());
        dto.addAll(powerSupplyService.isTop());
        dto.addAll(processorService.isTop());
        dto.addAll(softwareService.isTop());
        dto.addAll(storageService.isTop());
        Collections.shuffle(dto);
        if(dto.size()>size){
            return dto.subList(0,size);
        }
        return dto;
    }

    public List<Object> getDeals(Integer size){
        List<Object> dto = new ArrayList<>();
        dto.addAll(casingService.isDeal());
        dto.addAll(coolingService.isDeal());
        dto.addAll(desktopService.isDeal());
        dto.addAll(graphicsCardService.isDeal());
        dto.addAll(laptopService.isDeal());
        dto.addAll(memoryService.isDeal());
        dto.addAll(monitorService.isDeal());
        dto.addAll(motherBoardService.isDeal());
        dto.addAll(peripheralService.isDeal());
        dto.addAll(powerSupplyService.isDeal());
        dto.addAll(processorService.isDeal());
        dto.addAll(softwareService.isDeal());
        dto.addAll(storageService.isDeal());
        Collections.shuffle(dto);
        if(dto.size()>size){
            return dto.subList(0,size);
        }
        return dto;
    }

    public List<Object> search(String text,Integer size){
        List<Object> dto = new ArrayList<>();
        dto.addAll(casingService.search(text));
        dto.addAll(coolingService.search(text));
        dto.addAll(desktopService.search(text));
        dto.addAll(graphicsCardService.search(text));
        dto.addAll(laptopService.search(text));
        dto.addAll(memoryService.search(text));
        dto.addAll(monitorService.search(text));
        dto.addAll(motherBoardService.search(text));
        dto.addAll(peripheralService.search(text));
        dto.addAll(powerSupplyService.search(text));
        dto.addAll(processorService.search(text));
        dto.addAll(softwareService.search(text));
        dto.addAll(storageService.search(text));
        if(dto.size()>size){
            return dto.subList(0,size);
        }
        return dto;
    }
}