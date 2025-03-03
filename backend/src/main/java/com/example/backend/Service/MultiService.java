package com.example.backend.Service;

import com.example.backend.DTO.OrderRequestDTO;
import com.example.backend.DTO.OrderResponseDTO;
import com.example.backend.Entity.Order;
import com.example.backend.Service.Module.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MultiService {
    private final OrderService orderService;

public void orderReceived(OrderRequestDTO orderRequestDTO){
    if(orderRequestDTO.name().isEmpty()) {
        throw new IllegalArgumentException("메뉴 없음");
    }else if(orderRequestDTO.count() <= 0){
        throw new IllegalArgumentException("수량 없음");
    }
        orderService.orderReceived(orderRequestDTO.name(),orderRequestDTO.count());
    }

    public List<OrderResponseDTO> getOrder(){
        List<Order> orderList = orderService.getOrder();
        return orderList.stream().map(this::getDto).toList();
    }
    public OrderResponseDTO getDto(Order order){
        return OrderResponseDTO.builder().name(order.getName()).count(order.getCount()).build();
    }
}
