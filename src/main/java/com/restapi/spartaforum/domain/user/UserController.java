package com.restapi.spartaforum.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@Controller
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

//    @PostMapping("/signin")
//    public ResponseEntity<UserServiceMessage> signIn(@RequestBody @Valid SignInRequestDTO requestDto,
//                                                     HttpServletResponse response) {
//        ResponseEntity<UserServiceMessage> responseEntity = null;
//
//        try {
//            responseEntity = signInService.signIn(requestDto, response);
//        } catch (IllegalArgumentException e) {
//            log.error(e.getMessage(), responseEntity);
//        }
//        return responseEntity;
//    }

    @GetMapping("/login")
    public String signupPage() {
        return "login";
    }
}
