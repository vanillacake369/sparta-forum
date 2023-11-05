package com.restapi.spartaforum.service;

import com.restapi.spartaforum.domain.dto.QuestionMapper;
import com.restapi.spartaforum.domain.dto.QuestionRequestDto;
import com.restapi.spartaforum.domain.dto.QuestionResponseDto;
import com.restapi.spartaforum.domain.entity.Question;
import com.restapi.spartaforum.domain.entity.User;
import com.restapi.spartaforum.domain.repo.QuestionRepo;
import com.restapi.spartaforum.domain.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final UserRepo userRepo;

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
        QuestionResponseDto questionResponseDto = QuestionMapper.INSTANCE.questionToResponseDto(savedQuestion);

        // 반환
        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
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
}
