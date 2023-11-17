package com.restapi.spartaforum.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BoardRequestDto {
    private String title;

    private String author;

    private String password;

    private String content;
}
