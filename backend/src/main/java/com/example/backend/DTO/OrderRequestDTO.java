package com.example.backend.DTO;

import java.time.LocalDateTime;

public record OrderRequestDTO(String name, int count, LocalDateTime time) {
}
