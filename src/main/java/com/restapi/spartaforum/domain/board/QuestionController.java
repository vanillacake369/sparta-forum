package com.restapi.spartaforum.domain.board;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sparta-forum/questions")
public class QuestionController {
    private final BoardService boardService;

    @PostMapping("ask")
    public ResponseEntity<BoardResponseDto> createPost(@RequestBody BoardRequestDto requestDto) {
        return boardService.createPost(requestDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BoardResponseDto> getPost(@PathVariable Long postId) {
        return boardService.getPost(postId);
    }

    @GetMapping("")
    public ResponseEntity<List<BoardResponseDto>> getAllPosts() {
        return boardService.getAllPosts();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Boolean> updatePost(@PathVariable Long postId, @RequestBody BoardRequestDto requestDto) {
        return boardService.updatePost(postId, requestDto);
    }

    @DeleteMapping("")
    public void removePost(@RequestHeader("postId") Long postId, @RequestHeader("password") String password) {
        boardService.removePost(postId, password);
    }
}
