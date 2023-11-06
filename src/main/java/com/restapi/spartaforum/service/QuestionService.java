package com.restapi.spartaforum.service;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.HttpStatus.OK;

import com.restapi.spartaforum.domain.dto.QuestionMapper;
import com.restapi.spartaforum.domain.dto.QuestionRequestDto;
import com.restapi.spartaforum.domain.dto.QuestionResponseDto;
import com.restapi.spartaforum.domain.entity.Question;
import com.restapi.spartaforum.domain.entity.User;
import com.restapi.spartaforum.domain.repo.QuestionRepo;
import com.restapi.spartaforum.domain.repo.UserRepo;
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
    private final QuestionRepo questionRepo;
    private final UserRepo userRepo;
    private final QuestionMapper questionMapper = QuestionMapper.INSTANCE;

    /*
    게시글 조회
    1. author를 통해 고객 조회, 없다면 고객 생성
    2. 게시글 생성
    3. 생성한 게시글 저장
    4. 반환 DTO 매핑 이후 반환
     */
    public ResponseEntity<QuestionResponseDto> createPost(QuestionRequestDto requestDto) {
        String author = requestDto.getAuthor();
        String password = requestDto.getPassword();
        User user = getOrCreateUserIfNotExists(author, password);

        Question question = Question.builder()
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .password(requestDto.getPassword())
                .content(requestDto.getContent())
                .user(user).build();

        Question savedQuestion = questionRepo.save(question);

        QuestionResponseDto questionResponseDto = questionMapper.questionToResponseDto(savedQuestion);

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
        QuestionResponseDto responseDto = questionMapper.questionToResponseDto(foundQuestion);
        return new ResponseEntity<>(responseDto, OK);
    }

    /* 모든 게시글 가져오기 */
    @Transactional(readOnly = true)
    public ResponseEntity<List<QuestionResponseDto>> getAllPosts() {
        List<QuestionResponseDto> questionResponseDtos = questionRepo.findAll().stream()
                .map(question -> questionMapper.questionToResponseDto(question))
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

        if (!isCorrectPassword(foundQuestion.getPassword(), requestDto.getPassword())) {
            throw new IllegalArgumentException("찾으시는 게시글의 비밀번호와 일치하지 않습니다.");
        }

        Boolean result = foundQuestion.updateQuestion(requestDto.getTitle(), requestDto.getAuthor(),
                requestDto.getPassword(), requestDto.getContent());

        return new ResponseEntity<>(result, result == TRUE ? OK : NOT_MODIFIED);
    }

    /*
    게시글 삭제
    1. 조회 후, 없는 게시글인 경우 ResourceNotFoundException
    2. 비밀번호 검증, 틀리다면 IllegalArgumentException
    3. 게시글 삭제처리
     */
    public void removePost(Long postId, String password) {
        Question foundQuestion = findByIdOrThrowException(postId);

        if (!isCorrectPassword(foundQuestion.getPassword(), password)) {
            throw new IllegalArgumentException("찾으시는 게시글의 비밀번호와 일치하지 않습니다.");
        }

        questionRepo.deleteById(postId);
    }


    final User getOrCreateUserIfNotExists(String name, String password) {
        User user = userRepo.findUserByName(name);
        if (user == null) {
            User newUser = User.builder()
                    .name(name)
                    .password(password).build();
            return userRepo.save(newUser);
        }
        return user;
    }

    private boolean isCorrectPassword(String entityPassword, String dtoPassword) {
        if (entityPassword.equals(dtoPassword)) {
            return true;
        }
        return false;
    }

    private Question findByIdOrThrowException(Long postId) throws ResourceNotFoundException {
        return questionRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("ID %s번에 해당하는 질문게시글을 찾을 수 없습니다.", postId))
                );
    }
}
