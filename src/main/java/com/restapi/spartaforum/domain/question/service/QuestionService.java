package com.restapi.spartaforum.domain.question.service;

import static org.springframework.http.HttpStatus.OK;

import com.restapi.spartaforum.domain.question.dto.QuestionRequestDto;
import com.restapi.spartaforum.domain.question.dto.QuestionResponseDto;
import com.restapi.spartaforum.domain.question.entity.Question;
import com.restapi.spartaforum.domain.question.repository.QuestionRepository;
import com.restapi.spartaforum.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

	private final QuestionRepository questionRepository;

	private final UserRepository userRepository;

	/*
	게시글 조회
	1. author를 통해 고객 조회, 없다면 고객 생성
	2. 게시글 생성
	3. 생성한 게시글 저장
	4. 반환 DTO 매핑 이후 반환
	 */
	public ResponseEntity<QuestionResponseDto> createPost(QuestionRequestDto requestDto) {
		Question question = Question.builder()
			.title(requestDto.title())
			.body(requestDto.body())
			.build();

		Question savedQuestion = questionRepository.save(question);

		QuestionResponseDto questionResponseDto = QuestionResponseDto.of(savedQuestion);

		return new ResponseEntity<>(questionResponseDto, OK);
	}


	/*
	게시글 조회
	1. 조회, 없다면 ResourceNotFoundException
	*/
	@Transactional(readOnly = true)
	public ResponseEntity<QuestionResponseDto> getPost(Long postId) {
		// 게시글 찾고, 없다면 예외처리
		Question foundQuestion = findByIdOrThrowException(postId);
		QuestionResponseDto responseDto = QuestionResponseDto.of(foundQuestion);
		return new ResponseEntity<>(responseDto, OK);
	}

	/* 모든 게시글 가져오기 */
	@Transactional(readOnly = true)
	public ResponseEntity<List<QuestionResponseDto>> getAllPosts() {
		List<QuestionResponseDto> questionResponseDtos = questionRepository.findAll().stream()
			.map(QuestionResponseDto::of)
			.collect(Collectors.toList());
		return new ResponseEntity<>(questionResponseDtos, OK);
	}

	/*
	게시글 수정
	 1. 조회, 없다면  ResourceNotFoundException
	 2. 비밀번호 검증, 틀리다면  IllegalArgumentException
	 3. 더티체킹을 위한 update
	 4. 수정결과에 따라 status code 반환
	 */
	public ResponseEntity<Boolean> updatePost(Long postId, QuestionRequestDto requestDto) {
		Question foundQuestion = findByIdOrThrowException(postId);

		return new ResponseEntity<>(OK);
	}

	/*
	게시글 삭제
	1. 조회 후, 없는 게시글인 경우 ResourceNotFoundException
	2. 비밀번호 검증, 틀리다면 IllegalArgumentException
	3. 게시글 삭제처리
	 */
	public void removePost(Long postId, String password) {
		Question foundQuestion = findByIdOrThrowException(postId);
		questionRepository.deleteById(postId);
	}


	private boolean isCorrectPassword(String entityPassword, String dtoPassword) {
		return !entityPassword.equals(dtoPassword);
	}

	private Question findByIdOrThrowException(Long postId) throws ResourceNotFoundException {
		return questionRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException(
				String.format("ID %s번에 해당하는 질문게시글을 찾을 수 없습니다.", postId))
			);
	}
}
