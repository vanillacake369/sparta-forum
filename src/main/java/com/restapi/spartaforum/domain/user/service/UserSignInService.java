package com.restapi.spartaforum.domain.user.service;

import static org.springframework.http.HttpStatus.OK;

import com.restapi.spartaforum.domain.user.entity.User;
import com.restapi.spartaforum.domain.user.dto.SignInRequestDTO;
import com.restapi.spartaforum.domain.user.entity.UserServiceMessage;
import com.restapi.spartaforum.domain.user.repository.UserRepository;
import com.restapi.spartaforum.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSignInService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	public ResponseEntity<UserServiceMessage> signIn(SignInRequestDTO requestDto, HttpServletResponse response) {
		String name = requestDto.username();
		String password = requestDto.password();

		// User 존재 여부 확인
		User user = verifyIfDuplicated(name);

		// User 암호 확인
		verifyPassword(user.getPassword(), password);

		// JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
		String token = jwtUtil.createToken(name, user.getRole());
		jwtUtil.addJwtToCookie(token, response);

		return new ResponseEntity<>(UserServiceMessage.SIGNIN_SUCCESS, OK);
	}

	User verifyIfDuplicated(String name) {
		return userRepository.findUserByName(name).orElseThrow(
			() -> new IllegalArgumentException(UserServiceMessage.NOT_FOUND_USER.getMessage())
		);
	}

	private void verifyPassword(String userPassword, String inputPassword) {
		System.out.println("userPassword = " + userPassword);
		System.out.println("inputPassword = " + inputPassword);
		if (!passwordEncoder.matches(inputPassword, userPassword)) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}
}
