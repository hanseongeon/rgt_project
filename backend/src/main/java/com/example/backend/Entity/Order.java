package com.example.backend.Entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class Order {
    String name;
    int count;
    int status;

    @Builder
    public Order(String name, int count, int status){
        this.name = name;
        this.count = count;
        this.status = status;
    }
}
