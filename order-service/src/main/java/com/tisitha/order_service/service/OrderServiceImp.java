package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemResponseDTO;
import com.tisitha.order_service.dto.InventoryDTO;
import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;
import com.tisitha.order_service.exception.EmptyOrderException;
import com.tisitha.order_service.exception.ItemNotFoundException;
import com.tisitha.order_service.exception.StockOutException;
import com.tisitha.order_service.feign.InventoryClient;
import com.tisitha.order_service.model.Order;
import com.tisitha.order_service.model.OrderItem;
import com.tisitha.order_service.model.OrderState;
import com.tisitha.order_service.payload.MailBody;
import com.tisitha.order_service.producer.KafkaJsonProducer;
import com.tisitha.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{

    private final OrderRepository orderRepository;
    private final KafkaJsonProducer kafkaJsonProducer;
    private final InventoryClient inventoryClient;
    private final CartItemService cartItemService;

    @Override
    public OrderResponseDTO getOrderData(OrderGetRequestDTO requestDTO) {
        Sort sort = requestDTO.getDir().equalsIgnoreCase("asc")?Sort.by(requestDTO.getSortBy()).ascending():Sort.by(requestDTO.getSortBy()).descending();
        Pageable pageable = PageRequest.of(requestDTO.getPageNumber(), requestDTO.getPageSize(),sort);

        Page<Order> orderPage = orderRepository.findAll(pageable);
        return new OrderResponseDTO(orderPage.stream().toList(),orderPage.getTotalElements(),orderPage.getTotalPages(),orderPage.isLast());
    }

    @Override
    public OrderResponseDTO getOrdersByCustomer(String userId, OrderGetRequestDTO requestDTO) {
        Sort sort = requestDTO.getDir().equalsIgnoreCase("asc")?Sort.by(requestDTO.getSortBy()).ascending():Sort.by(requestDTO.getSortBy()).descending();
        Pageable pageable = PageRequest.of(requestDTO.getPageNumber(), requestDTO.getPageSize(),sort);

        Page<Order> orderPage = orderRepository.findAllByCustomerId(UUID.fromString(userId),pageable);
        return new OrderResponseDTO(orderPage.stream().toList(),orderPage.getTotalElements(),orderPage.getTotalPages(),orderPage.isLast());
    }

    @Override
    public void addOrder(String userId) {
        List<CartItemResponseDTO> dtos = cartItemService.getCartItems(userId);
        Order order = new Order();
        double cost = 0;
        if(dtos.isEmpty()){
            throw new EmptyOrderException("cannot enter empty order");
        }
        List<OrderItem> orderItemList = new ArrayList<>();
        int quantity;
        for(CartItemResponseDTO dto:dtos){
            try{
                quantity = inventoryClient.getQuantity(dto.getProductId()).getBody().getQuantity();
            } catch (Exception e) {
                cartItemService.deleteCartItem(userId,dto.getId());
                throw new ItemNotFoundException("Item not exits in inventory (product id of" +dto.getProductId()+")");
            }
            if (quantity < dto.getQuantity()) {
                for(OrderItem i:orderItemList){
                    quantity = Objects.requireNonNull(inventoryClient.getQuantity(i.getProductId()).getBody()).getQuantity();
                    inventoryClient.updateQuantity(i.getProductId(),quantity+i.getQuantity(),i.getTitle());
                }
                throw new StockOutException(dto.getTitle()+" (id:"+dto.getProductId()+") not enough stock");
            }
            inventoryClient.updateQuantity(dto.getProductId(),quantity-dto.getQuantity(),dto.getTitle());
            OrderItem orderItem = new OrderItem(
                    dto.getProductId(),
                    dto.getTitle(),
                    dto.getPrice(),
                    dto.getDeal(),
                    dto.getQuantity()
            );
            orderItemList.add(orderItem);
            if(dto.getDeal()!=0){
                cost += dto.getDeal()* dto.getQuantity();
            }
            else{
                cost += dto.getPrice()* dto.getQuantity();
            }
        }
        order.setItems(orderItemList);
        order.setCustomerId(UUID.fromString(userId));
        order.setDateTime(LocalDateTime.now());
        order.setOrderState(OrderState.PROCESSING);
        order.setCost(cost);
        Order newOrder = orderRepository.save(order);
        StringBuilder messageBody = new StringBuilder("Date and time:" + newOrder.getDateTime() + "\n" +
                        "Total cost: Rs." + newOrder.getCost() + "\n" + "\n" +
                        "Items:" + "\n");
        for(OrderItem i:newOrder.getItems()){
            messageBody.append(i.toString()).append("\n");
        }
        kafkaJsonProducer.sendJson(MailBody.builder()
                        .subject("New Order"+" (Order id:" + newOrder.getId()+")")
                        .text(messageBody.toString())
                        .build());
        for(CartItemResponseDTO dto:dtos){
            cartItemService.deleteCartItem(userId,dto.getId());
        }
    }

    @Override
    public void deleteOrderData(UUID id) {
        orderRepository.deleteById(id);
    }
}
