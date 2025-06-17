package com.tisitha.order_service.controller;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.CartItemResponseDTO;
import com.tisitha.order_service.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CartItemResponseDTO>> getCartItems(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(cartItemService.getCartItems(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addCartItem(@RequestBody CartItemRequestDTO dto) {
        return new ResponseEntity<>(cartItemService.addCart(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") UUID id) {
        cartItemService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestParam("uid") UUID id, @RequestParam Integer quantity) {
        cartItemService.updateCartItem(id, quantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}