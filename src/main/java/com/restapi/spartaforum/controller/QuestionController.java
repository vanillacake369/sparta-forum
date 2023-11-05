package com.restapi.spartaforum.controller;

import com.restapi.spartaforum.domain.dto.QuestionRequestDto;
import com.restapi.spartaforum.domain.dto.QuestionResponseDto;
import com.restapi.spartaforum.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sparta-forum/questions")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("ask")
    public ResponseEntity<QuestionResponseDto> createPost(@RequestBody QuestionRequestDto requestDto) {
        return questionService.createPost(requestDto);
    }
}
