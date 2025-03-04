package com.example.backend.DTO;

import lombok.Builder;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Builder
public record OrderResponseDTO(String name, int count, int status , LocalDateTime time, int index) {
}
