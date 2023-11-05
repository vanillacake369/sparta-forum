package com.restapi.spartaforum.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.restapi.spartaforum.domain.dto.QuestionResponseDto;
import com.restapi.spartaforum.domain.entity.Question;
import com.restapi.spartaforum.domain.entity.User;
import com.restapi.spartaforum.domain.repo.QuestionRepo;
import com.restapi.spartaforum.domain.repo.UserRepo;
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
    private QuestionRepo questionRepo;

    @Autowired
    private UserRepo userRepo;

    @Test
    @DisplayName("유저가 없다면 새로운 유저를 생성합니다")
    void 유저를_가져오거나_생성합니다() {
        // GIVEN
        String name = "jihoon";

        // WHEN
        User jihoon = questionService.getOrCreateUserIfNotExists("jihoon", "1234");

        // THEN
        assertEquals(name, jihoon.getName());
    }

    @Test
    @DisplayName("id에 해당하는 게시글을 찾습니다.")
    void 게시글을_찾고_존재하지_않는다면_예외를_처리() {
        // GIVEN
        Long id1 = 1L; // 저장한 경우
        Long id2 = 2L; // 저장하지 않은 경우

        User user = new User();
        User save = userRepo.save(user);
        Question question1 = Question.builder()
                .id(id1)
                .user(save)
                .build();
        questionRepo.save(question1);

        // WHEN
        ResponseEntity<QuestionResponseDto> responseEntity1 = questionService.getPost(id1);

        // THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            questionService.getPost(id2);
        });
        assertEquals(question1.getTitle(), responseEntity1.getBody().getTitle());
    }
}