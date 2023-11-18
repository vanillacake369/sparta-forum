package com.restapi.spartaforum.domain.user;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserSignInService signInService;

    @PostMapping("/signup")
    public ResponseEntity<UserServiceMessage> signUp(@RequestBody @Valid SignUpRequestDTO requestDto) {
        return signUpService.signUp(requestDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserServiceMessage> signIn(@RequestBody @Valid SignInRequestDTO requestDto,
                                                     HttpServletResponse response) {
        ResponseEntity<UserServiceMessage> responseEntity = null;
        try {
            responseEntity = signInService.signIn(requestDto, response);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), responseEntity);
        }
        return responseEntity;
    }
}
