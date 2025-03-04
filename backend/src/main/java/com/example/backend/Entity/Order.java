package com.example.backend.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Order {
    String name;
    int count;
    int status;
    LocalDateTime time;
    int index;
    @Builder
    public Order(String name, int count, int status,LocalDateTime time, int index){
        this.name = name;
        this.count = count;
        this.status = status;
        this.time = time;
        this.index = index;
    }
}
