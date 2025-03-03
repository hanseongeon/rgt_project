package com.example.backend.Controller;

import com.example.backend.DTO.OrderRequestDTO;
import com.example.backend.DTO.OrderResponseDTO;
import com.example.backend.Entity.Order;
import com.example.backend.Service.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final MultiService multiService;

    @PostMapping //주문접수
    public ResponseEntity<?> orderReceived(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            multiService.orderReceived(orderRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping //주문현황
    public ResponseEntity<?> checkOrder(){
        List<OrderResponseDTO> orderResponseDTOS = multiService.getOrder();
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTOS);
    }

    @MessageMapping("/api/orderList")
    @SendTo("/api/sub/orderList")
    public OrderResponseDTO realTimeOrder (OrderRequestDTO orderRequestDTO){
        return multiService.getDto(Order.builder().name(orderRequestDTO.name()).count(orderRequestDTO.count()).build());
    }

}
