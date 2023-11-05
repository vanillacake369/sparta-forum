package com.restapi.spartaforum.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.restapi.spartaforum.domain.entity.User;
import com.restapi.spartaforum.domain.repo.QuestionRepo;
import com.restapi.spartaforum.domain.repo.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
}