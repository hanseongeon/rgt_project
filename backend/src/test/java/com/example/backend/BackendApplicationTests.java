package com.example.backend;

import com.example.backend.DTO.OrderChangeRequestDTO;
import com.example.backend.DTO.OrderRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.time.LocalDateTime;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private WebSocketStompClient stompClient;
    private WebSocketHttpHeaders headers;

    @Test
    void contextLoads() {
    }

    @Test
        // 주문생성
    void createOrder() throws Exception {
        LocalDateTime orderTime = LocalDateTime.of(2025, 3, 5, 2, 2);
        OrderRequestDTO orderRequest = new OrderRequestDTO("테스트 상품", 5, orderTime);

        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())  // 200 OK 상태 코드 기대
                .andDo(print());  // 응답 내용 출력
    }

    @Test
        // 주문리스트 가져오기
    void getOrder() throws Exception {
        mockMvc.perform(get("/api/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200 OK 상태 코드 기대
                .andDo(print()); // 응답 내용 출력
    }

    @Test // 주문상태 변경
    void changeOrder() throws Exception {
        OrderChangeRequestDTO changeRequestDTO = new OrderChangeRequestDTO(0, 1);
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(put("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeRequestDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test // 주문삭제
    void deleteOrder() throws Exception {
        int index = 0;
        mockMvc.perform(delete("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(index)))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
