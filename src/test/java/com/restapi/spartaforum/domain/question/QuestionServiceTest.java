package com.restapi.spartaforum.domain.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.restapi.spartaforum.domain.question.dto.QuestionResponseDto;
import com.restapi.spartaforum.domain.question.entity.Question;
import com.restapi.spartaforum.domain.question.repository.QuestionRepository;
import com.restapi.spartaforum.domain.question.service.QuestionService;
import com.restapi.spartaforum.domain.user.entity.User;
import com.restapi.spartaforum.domain.user.repository.UserRepository;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;


@SpringBootTest
class QuestionServiceTest {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		questionRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}


	@Test
	@DisplayName("id에 해당하는 게시글을 찾습니다.")
	void 게시글을_찾고_존재하지_않는다면_예외를_처리() {
		// GIVEN
		// User,Question 저장한 경우
		Long id1 = 1L;
		User user = new User();
		User save = userRepository.save(user);
		Question question1 = Question.builder()
			.id(id1)
			.user(save)
			.build();
		questionRepository.save(question1);
		// 저장하지 않은 경우
		Long id2 = 2L;

		// WHEN
		ResponseEntity<QuestionResponseDto> responseEntity1 = questionService.getPost(id1);

		// THEN
		String title = Objects.requireNonNull(responseEntity1.getBody()).title();
		assertThrows(ResourceNotFoundException.class, () -> questionService.getPost(id2));
		assertEquals(question1.getTitle(), title);
	}
}