package com.restapi.spartaforum.domain.board;

import com.restapi.spartaforum.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/sparta-forum/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/{postId}")
    public ResponseEntity<BoardResponseDTO> getPost(@PathVariable Long postId) {
        return boardService.getPost(postId);
    }

    @GetMapping("")
    public ResponseEntity<List<BoardResponseDTO>> getAllPosts() {
        return boardService.getAllPosts();
    }

    @PostMapping("/post")
    public ResponseEntity<BoardResponseDTO> createPost(@RequestBody BoardRequestDTO requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = (User) authentication.getPrincipal();
        log.info(loggedUser.toString());
        return boardService.createPost(requestDto);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Boolean> updatePost(@PathVariable Long postId, @RequestBody BoardRequestDTO requestDto) {
        return boardService.updatePost(postId, requestDto);
    }

    @DeleteMapping("")
    public void removePost(@RequestHeader("postId") Long postId, @RequestHeader("password") String password) {
        boardService.removePost(postId, password);
    }
}
