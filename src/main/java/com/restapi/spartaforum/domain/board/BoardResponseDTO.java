package com.restapi.spartaforum.domain.board;

import java.time.LocalDateTime;

public record BoardResponseDTO(
        Long id,
        String title,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
}
