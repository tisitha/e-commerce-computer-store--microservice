package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemResponseDTO;
import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;
import com.tisitha.order_service.exception.EmptyOrderException;
import com.tisitha.order_service.exception.ItemNotFoundException;
import com.tisitha.order_service.exception.StockOutException;
import com.tisitha.order_service.feign.InventoryClient;
import com.tisitha.order_service.model.Order;
import com.tisitha.order_service.model.OrderItem;
import com.tisitha.order_service.model.OrderState;
import com.tisitha.order_service.producer.KafkaProducer;
import com.tisitha.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final KafkaProducer kafkaProducer;
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
        StringBuilder messageBody =new StringBuilder()
                .append("New Order Received\n")
                .append("Order ID: ").append(newOrder.getId()).append("\n")
                .append("Date & Time: ").append(newOrder.getDateTime()).append("\n")
                .append("Total Cost: Rs. ").append(newOrder.getCost()).append("\n\n")
                .append("Items:\n");
        for(OrderItem i:newOrder.getItems()){
            messageBody.append(i.toString()).append("\n");
        }
        kafkaProducer.send(messageBody.toString());
        for(CartItemResponseDTO dto:dtos){
            cartItemService.deleteCartItem(userId,dto.getId());
        }
    }

    @Override
    public void deleteOrderData(UUID id) {
        orderRepository.deleteById(id);
    }
}
