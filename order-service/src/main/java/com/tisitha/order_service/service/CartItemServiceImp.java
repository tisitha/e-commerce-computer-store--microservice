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
        if(dto.getQuantity()==0){
            return null;
        }
        CartItem item = cartItemRepository.findByProductIdAndCustomerId(dto.getProductId(),dto.getCustomerId());
        if(item==null){
            item = new CartItem();
        }
        item.setProductId(dto.getProductId());
        item.setTitle(dto.getTitle());
        item.setImgUrl(dto.getImgUrl());
        item.setPrice(dto.getPrice());
        item.setDeal(dto.getDeal());
        item.setQuantity(item.getQuantity()+dto.getQuantity());
        item.setCustomerId(dto.getCustomerId());
        CartItem newItem = cartItemRepository.save(item);
        return itemToDto(newItem);
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