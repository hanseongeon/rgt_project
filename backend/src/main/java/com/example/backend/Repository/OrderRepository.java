package com.example.backend.Repository;

import com.example.backend.Entity.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private final List<Order> orderList = new ArrayList<>();
    private int index = -1;
    public int save(String name, int count, LocalDateTime time) {
        index++;
        Order order = Order.builder().name(name).count(count).status(0).time(time).index(index).build();
        orderList.add(order);
        return index;
    }

    public List<Order> getOrder(){
        return orderList;
    }

    public List<Order> chnageStatus(int index,int status) {
        Order order = orderList.get(index);
        order.setStatus(status);
        orderList.set(index,order);
        return orderList;
    }

    public void deleteOrder(int index){
        orderList.remove(index);
    }
}
