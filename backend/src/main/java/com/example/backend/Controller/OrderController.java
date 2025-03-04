package com.example.backend.Controller;

import com.example.backend.DTO.OrderChangeRequestDTO;
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
        int index =   multiService.orderReceived(orderRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(index);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping //주문현황
    public ResponseEntity<?> checkOrder(){
        List<OrderResponseDTO> orderResponseDTOS = multiService.getOrder();
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTOS);
    }

    @PutMapping //주문 상태 변경
    public ResponseEntity<?> changeStatus(@RequestBody OrderChangeRequestDTO orderChangeRequestDTO){
        List<OrderResponseDTO> orderResponseDTOS =  multiService.changeStatus(orderChangeRequestDTO.index(),orderChangeRequestDTO.status());
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTOS);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteOrder(@RequestHeader int index){
        multiService.deleteOrder(index);
        return ResponseEntity.status(HttpStatus.OK).body("delete");
    }

    @MessageMapping("/orderList")
    @SendTo("/api/sub/orderList")
    public ResponseEntity<?> realTimeOrder (OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = multiService.getDto(Order.builder().name(orderRequestDTO.name()).count(orderRequestDTO.count()).build());
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
    }
}
