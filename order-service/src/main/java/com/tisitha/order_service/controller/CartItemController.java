package com.tisitha.order_service.controller;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.CartItemResponseDTO;
import com.tisitha.order_service.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get cart items of a user")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<CartItemResponseDTO>> getCartItems(@RequestHeader("Authorization") String authHeader,@PathVariable("id") UUID id) {
        return new ResponseEntity<>(cartItemService.getCartItems(authHeader,id), HttpStatus.OK);
    }

    @Operation(summary = "Add item to cart")
    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addCartItem(@RequestHeader("Authorization") String authHeader,@Valid @RequestBody CartItemRequestDTO dto) {
        return new ResponseEntity<>(cartItemService.addCart(authHeader,dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete item from cart")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@RequestHeader("Authorization") String authHeader,@PathVariable("id") UUID id) {
        cartItemService.deleteCartItem(authHeader, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update the quantity of a cart item")
    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestHeader("Authorization") String authHeader,@RequestParam("id") UUID id, @RequestParam Integer quantity) {
        cartItemService.updateCartItem(authHeader,id, quantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}