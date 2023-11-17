package com.restapi.spartaforum.domain.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.restapi.spartaforum.domain.user.User;
import com.restapi.spartaforum.domain.user.UserRepo;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;


@SpringBootTest
class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepo userRepo;

    @AfterEach
    void tearDown() {
        boardRepository.deleteAllInBatch();
        userRepo.deleteAllInBatch();
    }

    @Test
    @DisplayName("유저가 없다면 새로운 유저를 생성합니다")
    void 유저를_가져오거나_생성합니다() {
        // GIVEN
        String name = "jihoon";

        // WHEN
        User jihoon = boardService.getOrCreateUserIfNotExists("jihoon", "1234");

        // THEN
        assertEquals(name, jihoon.getName());
    }

    @Test
    @DisplayName("id에 해당하는 게시글을 찾습니다.")
    void 게시글을_찾고_존재하지_않는다면_예외를_처리() {
        // GIVEN
        // User,Question 저장한 경우
        Long id1 = 1L;
        User user = new User();
        User save = userRepo.save(user);
        Board question1 = Board.builder()
                .id(id1)
                .user(save)
                .build();
        boardRepository.save(question1);
        // 저장하지 않은 경우
        Long id2 = 2L;

        // WHEN
        ResponseEntity<BoardResponseDto> responseEntity1 = boardService.getPost(id1);

        // THEN
        String title = Objects.requireNonNull(responseEntity1.getBody()).getTitle();
        assertThrows(ResourceNotFoundException.class, () -> boardService.getPost(id2));
        assertEquals(question1.getTitle(), title);
    }
}