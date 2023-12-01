package com.restapi.spartaforum.domain.question.controller;

import com.restapi.spartaforum.domain.question.dto.QuestionRequestDto;
import com.restapi.spartaforum.domain.question.dto.QuestionResponseDto;
import com.restapi.spartaforum.domain.question.service.QuestionService;
import com.restapi.spartaforum.domain.user.entity.User;
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

	private final QuestionService questionService;

	@GetMapping("/{postId}")
	public ResponseEntity<QuestionResponseDto> getPost(@PathVariable Long postId) {
		return questionService.getPost(postId);
	}

	@GetMapping("")
	public ResponseEntity<List<QuestionResponseDto>> getAllPosts() {
		return questionService.getAllPosts();
	}

	@PostMapping("/post")
	public ResponseEntity<QuestionResponseDto> createPost(@RequestBody QuestionRequestDto requestDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = (User) authentication.getPrincipal();
		log.info(loggedUser.toString());
		return questionService.createPost(requestDto);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<Boolean> updatePost(@PathVariable Long postId, @RequestBody QuestionRequestDto requestDto) {
		return questionService.updatePost(postId, requestDto);
	}

	@DeleteMapping("")
	public void removePost(@RequestHeader("postId") Long postId, @RequestHeader("password") String password) {
		questionService.removePost(postId, password);
	}
}
