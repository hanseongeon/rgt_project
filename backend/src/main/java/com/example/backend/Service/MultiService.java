package com.example.backend.Service;

import com.example.backend.DTO.OrderRequestDTO;
import com.example.backend.DTO.OrderResponseDTO;
import com.example.backend.Entity.Order;
import com.example.backend.Service.Module.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MultiService {
    private final OrderService orderService;

    public int orderReceived(OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO.name().isEmpty()) {
            throw new IllegalArgumentException("메뉴 없음");
        } else if (orderRequestDTO.count() <= 0) {
            throw new IllegalArgumentException("수량 없음");
        }
      return   orderService.orderReceived(orderRequestDTO.name(), orderRequestDTO.count(), formatDate(orderRequestDTO.time()));
    }

    public List<OrderResponseDTO> getOrder() {
        List<Order> orderList = orderService.getOrder();
        return orderList.stream().map(this::getDto).toList();
    }

    public OrderResponseDTO getDto(Order order) {
        return OrderResponseDTO.builder().name(order.getName()).count(order.getCount()).status(order.getStatus()).time(order.getTime()).index(order.getIndex()).build();
    }

    public LocalDateTime formatDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm");
        String formatted = time.format(formatter);
        return LocalDateTime.parse(formatted, formatter);
    }

    public List<OrderResponseDTO> changeStatus(int index,int status){
       List<Order> orderList = orderService.changeStatus(index,status);
       return orderList.stream().map(this::getDto).toList();
    }

    public void deleteOrder(int index){
        orderService.deleteOrder(index);
    }
}
