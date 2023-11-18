package com.restapi.spartaforum.domain.user;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sparta-forum/user")
@Slf4j
public class UserController {
    private final UserSignUpService signUpService;
    private ExtendedLogger logger;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> createPost(@RequestBody @Valid UserSignUpRequestDTO requestDto) {
        ResponseEntity<UserSignUpResponseDTO> user = new ResponseEntity<>(BAD_REQUEST);
        try {
            user = signUpService.createUser(requestDto);
        } catch (Exception e) {
            logger.error(e);
        }
        return user;
    }
}
