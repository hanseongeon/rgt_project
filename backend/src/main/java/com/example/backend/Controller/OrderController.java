package com.example.backend.Controller;

import com.example.backend.DTO.OrderRequestDTO;
import com.example.backend.DTO.OrderResponseDTO;
import com.example.backend.Service.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final MultiService multiService;

    @PostMapping
    public ResponseEntity<?> orderReceived(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            multiService.orderReceived(orderRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> checkOrder(){
        List<OrderResponseDTO> orderResponseDTOS = multiService.getOrder();
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTOS);
    }
}
