package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.CartItemResponseDTO;
import com.tisitha.order_service.exception.ItemNotFoundException;
import com.tisitha.order_service.model.CartItem;
import com.tisitha.order_service.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImp implements CartItemService{

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImp(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItemResponseDTO> getCartItems(UUID id) {
        List<CartItem> cartItems = cartItemRepository.findAllByCustomerId(id);
        return cartItems.stream().map(this::itemToDto).toList();
    }

    CartItemResponseDTO itemToDto(CartItem item){
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setTitle(item.getTitle());
        dto.setImgUrl(item.getImgUrl());
        dto.setPrice(item.getPrice());
        dto.setDeal(item.getDeal());
        dto.setQuantity(item.getQuantity());
        dto.setCustomerId(item.getCustomerId());
        return dto;
    }

    @Override
    public CartItemResponseDTO addCart(CartItemRequestDTO dto) {
        CartItem items = new CartItem();
        items.setProductId(dto.getProductId());
        items.setTitle(dto.getTitle());
        items.setImgUrl(dto.getImgUrl());
        items.setPrice(dto.getPrice());
        items.setDeal(dto.getDeal());
        items.setQuantity(dto.getQuantity());
        items.setCustomerId(dto.getCustomerId());
        CartItem newItems = cartItemRepository.save(items);
        return itemToDto(newItems);
    }

    @Override
    public void deleteCartItem(UUID id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public void updateCartItem(UUID id, Integer quantity) {
        CartItem item = cartItemRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }
}