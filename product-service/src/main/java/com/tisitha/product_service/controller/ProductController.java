package com.tisitha.product_service.controller;

import com.tisitha.product_service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final CasingService casingService;
    private final CoolingService coolingService;
    private final DesktopService desktopService;
    private final GraphicCardService graphicCardService;
    private final LaptopService laptopService;
    private final MemoryService memoryService;
    private final MonitorService monitorService;
    private final MotherBoardService motherBoardService;
    private final PeripheralService peripheralService;
    private final PowerSupplyService powerSupplyService;
    private final ProcessorService processorService;
    private final SoftwareService softwareService;
    private final StorageService storageService;

    @GetMapping("/get-new")
    public ResponseEntity<List<Object>> getNew(){
        List<Object> dto = new ArrayList<>();
        dto.add(casingService.isNew());
        dto.add(coolingService.isNew());
        dto.add(desktopService.isNew());
        dto.add(graphicCardService.isNew());
        dto.add(laptopService.isNew());
        dto.add(memoryService.isNew());
        dto.add(monitorService.isNew());
        dto.add(motherBoardService.isNew());
        dto.add(peripheralService.isNew());
        dto.add(powerSupplyService.isNew());
        dto.add(processorService.isNew());
        dto.add(softwareService.isNew());
        dto.add(storageService.isNew());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/get-top-picks")
    public ResponseEntity<List<Object>> getTop(){
        List<Object> dto = new ArrayList<>();
        dto.add(casingService.isTop());
        dto.add(coolingService.isTop());
        dto.add(desktopService.isTop());
        dto.add(graphicCardService.isTop());
        dto.add(laptopService.isTop());
        dto.add(memoryService.isTop());
        dto.add(monitorService.isTop());
        dto.add(motherBoardService.isTop());
        dto.add(peripheralService.isTop());
        dto.add(powerSupplyService.isTop());
        dto.add(processorService.isTop());
        dto.add(softwareService.isTop());
        dto.add(storageService.isTop());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/get-deals")
    public ResponseEntity<List<Object>> getDeals(){
        List<Object> dto = new ArrayList<>();
        dto.add(casingService.isDeal());
        dto.add(coolingService.isDeal());
        dto.add(desktopService.isDeal());
        dto.add(graphicCardService.isDeal());
        dto.add(laptopService.isDeal());
        dto.add(memoryService.isDeal());
        dto.add(monitorService.isDeal());
        dto.add(motherBoardService.isDeal());
        dto.add(peripheralService.isDeal());
        dto.add(powerSupplyService.isDeal());
        dto.add(processorService.isDeal());
        dto.add(softwareService.isDeal());
        dto.add(storageService.isDeal());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
