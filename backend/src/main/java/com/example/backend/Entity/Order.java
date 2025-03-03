package com.example.backend.Entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class Order {
    String name;
    int count;

    @Builder
    public Order(String name, int count){
        this.name = name;
        this.count = count;
    }
}
