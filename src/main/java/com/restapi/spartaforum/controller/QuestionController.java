package com.restapi.spartaforum.controller;

import com.restapi.spartaforum.domain.dto.QuestionRequestDto;
import com.restapi.spartaforum.domain.dto.QuestionResponseDto;
import com.restapi.spartaforum.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("{postId}")
    public ResponseEntity<QuestionResponseDto> getPost(@PathVariable Long postId) {
        return questionService.getPost(postId);
    }

    @GetMapping("")
    public ResponseEntity<List<QuestionResponseDto>> getAllPosts() {
        return questionService.getAllPosts();
    }

    @PutMapping("{postId}")
    public ResponseEntity<Boolean> updatePost(@PathVariable Long postId, @RequestBody QuestionRequestDto requestDto) {
        return questionService.updatePost(postId, requestDto);
    }
}
