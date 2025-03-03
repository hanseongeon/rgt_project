package com.example.backend.Service.Module;

import com.example.backend.Entity.Order;
import com.example.backend.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void orderReceived(String name, int count){
        orderRepository.save(name,count);
    }

    public List<Order> getOrder(){
        return orderRepository.getOrder();
    }
}
