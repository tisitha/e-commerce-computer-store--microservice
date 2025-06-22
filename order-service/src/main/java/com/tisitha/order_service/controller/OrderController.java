package com.tisitha.order_service.controller;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;
import com.tisitha.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<OrderResponseDTO> getOrders(@RequestBody OrderGetRequestDTO requestDTO){
        return new ResponseEntity<>(orderService.getOrderData(requestDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrdersByCustomer(@PathVariable UUID id,@RequestBody OrderGetRequestDTO requestDTO){
        return new ResponseEntity<>(orderService.getOrdersByCustomer(id,requestDTO), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addOrders(@RequestBody List<CartItemRequestDTO> dtos){
        orderService.addOrder(dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderList(@PathVariable UUID id){
        orderService.deleteOrderData(id);
        return ResponseEntity.ok().build();
    }
}
