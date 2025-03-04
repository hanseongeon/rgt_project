package com.example.backend.Service.Module;

import com.example.backend.Entity.Order;
import com.example.backend.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public int orderReceived(String name, int count, LocalDateTime time){
        return orderRepository.save(name,count,time);
    }

    public List<Order> getOrder(){
        return orderRepository.getOrder();
    }

    public List<Order> changeStatus(int index,int status){
        return orderRepository.chnageStatus(index,status);
    }

    public void deleteOrder(int index){
        orderRepository.deleteOrder(index);
    }
}
