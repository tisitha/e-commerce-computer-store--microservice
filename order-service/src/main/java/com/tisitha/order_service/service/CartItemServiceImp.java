package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.CartItemResponseDTO;
import com.tisitha.order_service.dto.ProductDTO;
import com.tisitha.order_service.exception.ItemNotFoundException;
import com.tisitha.order_service.exception.UnauthorizeUserException;
import com.tisitha.order_service.feign.ProductClient;
import com.tisitha.order_service.model.CartItem;
import com.tisitha.order_service.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImp implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;

    public CartItemServiceImp(CartItemRepository cartItemRepository, ProductClient productClient) {
        this.cartItemRepository = cartItemRepository;
        this.productClient = productClient;
    }

    @Override
    public List<CartItemResponseDTO> getCartItems(String userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByCustomerId(UUID.fromString(userId));
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
        dto.setCategory(item.getCategory());
        dto.setQuantity(item.getQuantity());
        dto.setCustomerId(item.getCustomerId());
        return dto;
    }

    @Override
    public CartItemResponseDTO addCart(String userId,CartItemRequestDTO dto) {
        if(dto.getQuantity()==0){
            return null;
        }
        CartItem item = cartItemRepository.findByProductIdAndCustomerId(dto.getProductId(),UUID.fromString(userId));
        if(item==null){
            item = new CartItem();
        }
        ProductDTO productDTO = getProductDetails(dto.getProductId(),dto.getCategory());
        if(productDTO==null){
            throw new ItemNotFoundException("Product not found (id: "+dto.getProductId()+",category: "+dto.getCategory()+")");
        }
        item.setProductId(productDTO.getId());
        item.setTitle(productDTO.getName());
        item.setImgUrl(productDTO.getImgUrl());
        item.setPrice(productDTO.getPrice());
        item.setDeal(productDTO.getDeal());
        item.setCategory(productDTO.getCategory());
        item.setQuantity(item.getQuantity()+dto.getQuantity());
        item.setCustomerId(UUID.fromString(userId));
        CartItem newItem = cartItemRepository.save(item);
        return itemToDto(newItem);
    }

    @Override
    public void deleteCartItem(String userId,UUID id) {
        CartItem ct = cartItemRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Item of id:"+id+" not found"));
        if(!UUID.fromString(userId).equals(ct.getCustomerId())){
            throw new UnauthorizeUserException("Unauthorize user request");
        }
        cartItemRepository.deleteById(id);
    }

    @Override
    public void updateCartItem(String userId,UUID id, Integer quantity) {
        CartItem item = cartItemRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Item not found"));
        if(!UUID.fromString(userId).equals(item.getCustomerId())){
            throw new UnauthorizeUserException("Unauthorize user request");
        }
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    private ProductDTO getProductDetails(UUID id, String category) {
        return switch (category) {
            case "Casing" -> productClient.getProductCasings(id).getBody();
            case "Cooling" -> productClient.getProductCooling(id).getBody();
            case "Desktop" -> productClient.getProductDesktops(id).getBody();
            case "GraphicsCard" -> productClient.getProductGraphics(id).getBody();
            case "Laptop" -> productClient.getProductLaptops(id).getBody();
            case "Memory" -> productClient.getProductMemory(id).getBody();
            case "Monitor" -> productClient.getProductMonitor(id).getBody();
            case "MotherBoard" -> productClient.getProductMotherBoards(id).getBody();
            case "Peripheral" -> productClient.getProductPeripherals(id).getBody();
            case "PowerSupply" -> productClient.getProductPower(id).getBody();
            case "Processor" -> productClient.getProductProcessors(id).getBody();
            case "Software" -> productClient.getProductSoftware(id).getBody();
            case "Storage" -> productClient.getProductStorages(id).getBody();
            default -> null;
        };
    }
}