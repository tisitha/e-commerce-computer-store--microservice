package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.CartItemResponseDTO;
import com.tisitha.order_service.exception.ItemNotFoundException;
import com.tisitha.order_service.exception.UnauthorizeUserException;
import com.tisitha.order_service.feign.UserClient;
import com.tisitha.order_service.model.CartItem;
import com.tisitha.order_service.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImp implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final UserClient userClient;

    public CartItemServiceImp(CartItemRepository cartItemRepository, UserClient userClient) {
        this.cartItemRepository = cartItemRepository;
        this.userClient = userClient;
    }

    @Override
    public List<CartItemResponseDTO> getCartItems(String authHeader,UUID id) {
        if(Boolean.FALSE.equals(userClient.validateTokenSubject(authHeader, id).getBody())){
            throw new UnauthorizeUserException("Unauthorize user request");
        }
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
    public CartItemResponseDTO addCart(String authHeader,CartItemRequestDTO dto) {
        if(Boolean.FALSE.equals(userClient.validateTokenSubject(authHeader, dto.getCustomerId()).getBody())){
            throw new UnauthorizeUserException("Unauthorize user request");
        }
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
    public void deleteCartItem(String authHeader,UUID id) {
        CartItem ct = cartItemRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Item of id:"+id+" not found"));
        if(Boolean.FALSE.equals(userClient.validateTokenSubject(authHeader, ct.getCustomerId()).getBody())){
            throw new UnauthorizeUserException("Unauthorize user request");
        }
        cartItemRepository.deleteById(id);
    }

    @Override
    public void updateCartItem(String authHeader,UUID id, Integer quantity) {
        CartItem item = cartItemRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Item not found"));
        if(Boolean.FALSE.equals(userClient.validateTokenSubject(authHeader, item.getCustomerId()).getBody())){
            throw new UnauthorizeUserException("Unauthorize user request");
        }
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }
}