package com.restapi.spartaforum.domain.board;

import java.time.LocalDateTime;

public record BoardResponseDto(
        Long id,
        String title,
        String author,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {


}
