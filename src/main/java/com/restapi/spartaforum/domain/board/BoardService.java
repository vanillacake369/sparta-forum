package com.restapi.spartaforum.domain.board;

import static org.springframework.http.HttpStatus.OK;

import com.restapi.spartaforum.domain.user.UserRepository;
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
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardMapper boardMapper = BoardMapper.INSTANCE;

    /*
    게시글 조회
    1. author를 통해 고객 조회, 없다면 고객 생성
    2. 게시글 생성
    3. 생성한 게시글 저장
    4. 반환 DTO 매핑 이후 반환
     */
    public ResponseEntity<BoardResponseDTO> createPost(BoardRequestDTO requestDto) {
        Board question = Board.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .build();

        Board savedBoard = boardRepository.save(question);

        BoardResponseDTO boardResponseDto = boardMapper.questionToResponseDto(savedBoard);

        return new ResponseEntity<>(boardResponseDto, OK);
    }


    /*
    게시글 조회
    1. 조회, 없다면 ResourceNotFoundException
    */
    @Transactional(readOnly = true)
    public ResponseEntity<BoardResponseDTO> getPost(Long postId) {
        // 게시글 찾고, 없다면 예외처리
        Board foundBoard = findByIdOrThrowException(postId);
        BoardResponseDTO responseDto = boardMapper.questionToResponseDto(foundBoard);
        return new ResponseEntity<>(responseDto, OK);
    }

    /* 모든 게시글 가져오기 */
    @Transactional(readOnly = true)
    public ResponseEntity<List<BoardResponseDTO>> getAllPosts() {
        List<BoardResponseDTO> boardResponseDTOS = boardRepository.findAll().stream()
                .map(boardMapper::questionToResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(boardResponseDTOS, OK);
    }

    /*
    게시글 수정
     1. 조회, 없다면  ResourceNotFoundException
     2. 비밀번호 검증, 틀리다면  IllegalArgumentException
     3. 더티체킹을 위한 update
     4. 수정결과에 따라 status code 반환
     */
    public ResponseEntity<Boolean> updatePost(Long postId, BoardRequestDTO requestDto) {
        Board foundBoard = findByIdOrThrowException(postId);

        return new ResponseEntity<>(OK);
    }

    /*
    게시글 삭제
    1. 조회 후, 없는 게시글인 경우 ResourceNotFoundException
    2. 비밀번호 검증, 틀리다면 IllegalArgumentException
    3. 게시글 삭제처리
     */
    public void removePost(Long postId, String password) {
        Board foundQuestion = findByIdOrThrowException(postId);
        boardRepository.deleteById(postId);
    }


    private boolean isCorrectPassword(String entityPassword, String dtoPassword) {
        return !entityPassword.equals(dtoPassword);
    }

    private Board findByIdOrThrowException(Long postId) throws ResourceNotFoundException {
        return boardRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("ID %s번에 해당하는 질문게시글을 찾을 수 없습니다.", postId))
                );
    }
}
