package com.restapi.spartaforum.domain.board;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor // 기본생성자,,,를 만드므로 맘에 안 듬,,,기능 구현 이후 지울 것
public class BoardResponseDto {
    private Long id;

    private String title;

    private String author;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
