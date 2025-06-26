package com.tisitha.order_service.controller;

import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;
import com.tisitha.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/admin/order")
    public ResponseEntity<OrderResponseDTO> getOrders(@Valid @RequestBody OrderGetRequestDTO requestDTO){
        return new ResponseEntity<>(orderService.getOrderData(requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get orders of a user")
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponseDTO> getOrdersByCustomer(@PathVariable UUID id,@Valid @RequestBody OrderGetRequestDTO requestDTO){
        return new ResponseEntity<>(orderService.getOrdersByCustomer(id,requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Add new order")
    @PostMapping("/order/{cid}")
    public ResponseEntity<Void> addOrders(@RequestHeader("Authorization") String authHeader,@PathVariable UUID cid){
        orderService.addOrder(authHeader,cid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a order")
    @DeleteMapping("/admin/order/delete/{id}")
    public ResponseEntity<Void> deleteOrderList(@PathVariable UUID id){
        orderService.deleteOrderData(id);
        return ResponseEntity.ok().build();
    }
}
