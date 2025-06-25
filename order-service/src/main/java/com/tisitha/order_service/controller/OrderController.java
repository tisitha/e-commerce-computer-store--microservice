package com.tisitha.order_service.controller;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;
import com.tisitha.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all orders")
    @GetMapping
    public ResponseEntity<OrderResponseDTO> getOrders(@RequestBody OrderGetRequestDTO requestDTO){
        return new ResponseEntity<>(orderService.getOrderData(requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get orders of a user")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrdersByCustomer(@PathVariable UUID id,@RequestBody OrderGetRequestDTO requestDTO){
        return new ResponseEntity<>(orderService.getOrdersByCustomer(id,requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Add new order")
    @PostMapping
    public ResponseEntity<Void> addOrders(@RequestHeader("Authorization") String authHeader,@RequestBody List<CartItemRequestDTO> dtos){
        orderService.addOrder(authHeader,dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a order")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderList(@PathVariable UUID id){
        orderService.deleteOrderData(id);
        return ResponseEntity.ok().build();
    }
}
