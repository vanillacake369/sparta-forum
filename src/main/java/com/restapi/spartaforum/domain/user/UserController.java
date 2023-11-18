package com.restapi.spartaforum.domain.user;

import com.restapi.spartaforum.domain.validator.UserStatusMessage;
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
    public ResponseEntity<UserStatusMessage> createPost(@RequestBody @Valid SignUpRequestDTO requestDto) {
        return signUpService.signUp(requestDto);
    }
}
