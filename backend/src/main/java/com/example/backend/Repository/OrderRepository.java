package com.example.backend.Repository;

import com.example.backend.Entity.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private final List<Order> orderList = new ArrayList<>();

    public void save(String name, int count) {
        Order order = Order.builder().name(name).count(count).build();
        orderList.add(order);
    }

    public List<Order> getOrder(){
        return orderList;
    }
}
