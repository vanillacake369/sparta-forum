package com.restapi.spartaforum.service;

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

    public ResponseEntity<QuestionResponseDto> createPost(QuestionRequestDto requestDto) {
        // author를 통해 고객 조회, 없다면 고객 생성
        String author = requestDto.getAuthor();
        String password = requestDto.getPassword();
        User user = getOrCreateUserIfNotExists(author, password);

        // 게시글 생성
        Question question = Question.builder()
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .password(requestDto.getPassword())
                .content(requestDto.getContent())
                .user(user).build();

        // 게시글 저장
        Question savedQuestion = questionRepo.save(question);

        // 반환 DTO 매핑
        QuestionResponseDto questionResponseDto = questionMapper.questionToResponseDto(savedQuestion);

        // 반환
        return new ResponseEntity<>(questionResponseDto, OK);
    }

    final User getOrCreateUserIfNotExists(String name, String password) {
        User user = userRepo.findUserByName(name);
        if (user == null) {
            user = userRepo.save(
                    User.builder()
                            .name(name)
                            .password(password).build()
            );
        }
        return user;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<QuestionResponseDto> selectPost(Long postId) {
        // 게시글 찾고, 없다면 예외처리
        Question foundQuestion = questionRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("%s ID에 해당하는 질문게시글을 찾을 수 없습니다.", postId))
                );
        QuestionResponseDto responseDto = questionMapper.questionToResponseDto(foundQuestion);
        return new ResponseEntity<>(responseDto, OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<QuestionResponseDto>> selectAllPosts() {
        List<QuestionResponseDto> questionResponseDtos = questionRepo.findAll().stream()
                .map(question -> questionMapper.questionToResponseDto(question))
                .collect(Collectors.toList());
        return new ResponseEntity<>(questionResponseDtos, OK);
    }
}
