package com.example.backend.DTO;

import lombok.Builder;

@Builder
public record OrderResponseDTO(String name, int count) {
}
