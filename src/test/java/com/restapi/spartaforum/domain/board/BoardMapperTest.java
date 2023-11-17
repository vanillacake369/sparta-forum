package com.restapi.spartaforum.domain.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.restapi.spartaforum.SpartaForumApplication;
import com.restapi.spartaforum.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SpartaForumApplication.class})
class BoardMapperTest {

    private final BoardMapper boardMapper = BoardMapper.INSTANCE;

    @Test
    @DisplayName("questionRequestDto를 Question으로 변환해줍니다.")
    void 질문요청dto를_질문객체로_변환() {
        // GIVEN
        String expectedTitle = "JPA Question";
        BoardRequestDto boardRequestDto = new BoardRequestDto("JPA Question", "jihoon", "1234",
                "I wonder about persistence context");

        // WHEN
        Board mappedQuestion = boardMapper.requestDtoToQuestion(boardRequestDto);

        // THEN
        assertEquals(expectedTitle, mappedQuestion.getTitle());
    }

    @Test
    @DisplayName("Question을 questionResponseDto로 변환해줍니다.")
    void 질문객체를_responseDto로_변환() {
        // GIVEN
        Board question = Board.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .password("1234")
                .content("content")
                .user(new User())
                .build();

        // WHEN
        BoardResponseDto responseDto = boardMapper.questionToResponseDto(question);

        // THEN
        assertEquals(question.getTitle(), responseDto.getTitle());
    }
}