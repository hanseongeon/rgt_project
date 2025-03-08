package com.example.backend.Repository;

import com.example.backend.Entity.Order;
import com.example.backend.Exceptions.DataNotSameException;
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
        if(orderList.isEmpty()){
            index = -1;
        }
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
        if(order == null){
            throw new DataNotSameException("해당 주문이 없습니다,");
        }
            order.setStatus(status);
            orderList.set(index,order);
            return orderList;


    }

    public void deleteOrder(int index){
        if(index < 0) {
            throw new IllegalArgumentException("해당 주문이 없습니다.");
        }
        for(Order order : orderList){
            if(order.getIndex() > index){
                int newIndex = order.getIndex();
                order.setIndex(newIndex - 1 );
                orderList.set(newIndex,order);
            }
        }
        orderList.remove(index);
    }
}
