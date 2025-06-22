package com.tisitha.order_service.service;

import com.tisitha.order_service.dto.CartItemRequestDTO;
import com.tisitha.order_service.dto.OrderGetRequestDTO;
import com.tisitha.order_service.dto.OrderResponseDTO;
import com.tisitha.order_service.model.Order;
import com.tisitha.order_service.model.OrderItem;
import com.tisitha.order_service.model.OrderState;
import com.tisitha.order_service.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDTO getOrderData(OrderGetRequestDTO requestDTO) {
        Sort sort = requestDTO.getDir().equalsIgnoreCase("asc")?Sort.by(requestDTO.getSortBy()).ascending():Sort.by(requestDTO.getSortBy()).descending();
        Pageable pageable = PageRequest.of(requestDTO.getPageNumber(), requestDTO.getPageSize(),sort);

        Page<Order> orderPage = orderRepository.findAll(pageable);
        return new OrderResponseDTO(orderPage.stream().toList(),orderPage.getTotalElements(),orderPage.getTotalPages(),orderPage.isLast());
    }

    @Override
    public OrderResponseDTO getOrdersByCustomer(UUID id, OrderGetRequestDTO requestDTO) {
        Sort sort = requestDTO.getDir().equalsIgnoreCase("asc")?Sort.by(requestDTO.getSortBy()).ascending():Sort.by(requestDTO.getSortBy()).descending();
        Pageable pageable = PageRequest.of(requestDTO.getPageNumber(), requestDTO.getPageSize(),sort);

        Page<Order> orderPage = orderRepository.findAllByCustomerId(id,pageable);
        return new OrderResponseDTO(orderPage.stream().toList(),orderPage.getTotalElements(),orderPage.getTotalPages(),orderPage.isLast());
    }

    @Override
    public void addOrder(List<CartItemRequestDTO> dtos) {
        Order order = new Order();
        double cost = 0;
        UUID customerId = null;
        List<OrderItem> orderItemList = new ArrayList<>();
        for(CartItemRequestDTO dto:dtos){
            if(customerId==null){
                customerId=dto.getCustomerId();
            }
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
        order.setCustomerId(customerId);
        order.setDateTime(LocalDateTime.now());
        order.setOrderState(OrderState.PROCESSING);
        order.setCost(cost);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrderData(UUID id) {
        orderRepository.deleteById(id);
    }
}
