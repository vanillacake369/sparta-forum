package com.restapi.spartaforum.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class QuestionRequestDto {
    private String title;

    private String author;

    private String password;

    private String content;
}
