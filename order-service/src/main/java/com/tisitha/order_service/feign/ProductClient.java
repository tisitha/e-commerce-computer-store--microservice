package com.tisitha.order_service.feign;

import com.tisitha.order_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/product/category/casings/item/{id}")
    ResponseEntity<ProductDTO> getProductCasings(@PathVariable UUID id);

    @GetMapping("/product/category/cooling/item/{id}")
    ResponseEntity<ProductDTO> getProductCooling(@PathVariable UUID id);

    @GetMapping("/product/category/desktops/item/{id}")
    ResponseEntity<ProductDTO> getProductDesktops(@PathVariable UUID id);

    @GetMapping("/product/category/graphics-cards/item/{id}")
    ResponseEntity<ProductDTO> getProductGraphics(@PathVariable UUID id);

    @GetMapping("/product/category/laptops/item/{id}")
    ResponseEntity<ProductDTO> getProductLaptops(@PathVariable UUID id);

    @GetMapping("/product/category/memory/item/{id}")
    ResponseEntity<ProductDTO> getProductMemory(@PathVariable UUID id);

    @GetMapping("/product/category/monitors/item/{id}")
    ResponseEntity<ProductDTO> getProductMonitor(@PathVariable UUID id);

    @GetMapping("/product/category/mother-boards/item/{id}")
    ResponseEntity<ProductDTO> getProductMotherBoards(@PathVariable UUID id);

    @GetMapping("/product/category/peripherals/item/{id}")
    ResponseEntity<ProductDTO> getProductPeripherals(@PathVariable UUID id);

    @GetMapping("/product/category/power/item/{id}")
    ResponseEntity<ProductDTO> getProductPower(@PathVariable UUID id);

    @GetMapping("/product/category/processors/item/{id}")
    ResponseEntity<ProductDTO> getProductProcessors(@PathVariable UUID id);

    @GetMapping("/product/category/software/item/{id}")
    ResponseEntity<ProductDTO> getProductSoftware(@PathVariable UUID id);

    @GetMapping("/product/category/storages/item/{id}")
    ResponseEntity<ProductDTO> getProductStorages(@PathVariable UUID id);
}
