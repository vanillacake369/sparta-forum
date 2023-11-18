package com.restapi.spartaforum.domain.user;

import static org.springframework.http.HttpStatus.OK;

import com.restapi.spartaforum.domain.validator.UserStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignUpService {
    private final UserRepository userRepository;

    public ResponseEntity<UserSignUpResponseDTO> createUser(UserSignUpRequestDTO requestDto) {
        User newUser = User.of(requestDto);
        userRepository.save(newUser);
        UserSignUpResponseDTO signUpResponseDTO = new UserSignUpResponseDTO(
                UserStatusMessage.SIGNUP_SUCCESS.getErrorMessage());
        return new ResponseEntity<>(signUpResponseDTO, OK);
    }
}
