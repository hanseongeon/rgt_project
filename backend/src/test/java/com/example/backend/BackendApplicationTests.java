package com.example.backend;

import com.example.backend.DTO.OrderRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void createOrder() throws Exception {
        LocalDateTime orderTime = LocalDateTime.of(2025, 3, 5, 2, 2);
        OrderRequestDTO orderRequest = new OrderRequestDTO("테스트 상품", 5, orderTime);

        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/api/order")  // URL을 실제 엔드포인트에 맞게 수정하세요
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())  // 200 OK 상태 코드 기대
                .andExpect(content().string(CoreMatchers.any(String.class)))  // 문자열 형태의 응답 기대
                .andDo(print());  // 응답 내용 출력
    }
}
