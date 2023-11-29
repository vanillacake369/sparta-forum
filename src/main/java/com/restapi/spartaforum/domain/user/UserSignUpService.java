package com.restapi.spartaforum.domain.user;

import static org.springframework.http.HttpStatus.OK;

import com.restapi.spartaforum.domain.user.dto.SignUpRequestDTO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<UserServiceMessage> signUp(SignUpRequestDTO requestDto) {
		String name = requestDto.name();
		String password = requestDto.password();

		// 중복 확인
		verifyIfDuplicated(name, password);

		// 사용자 ROLE 확인
		UserRoleEnum role = getRoleByToken(requestDto.isAdmin(), requestDto.adminToken());

		User newUser = User.of(name, passwordEncoder.encode(password), role);
		userRepository.save(newUser);
		return new ResponseEntity<>(UserServiceMessage.SIGNUP_SUCCESS, OK);
	}

	UserRoleEnum getRoleByToken(boolean isAdmin, String adminToken) {
		if (isAdmin) {
			if (User.hasNotAdminToken(adminToken)) {
				throw new IllegalArgumentException("관리자 권한을 위한 암호가 틀립니다.");
			}
			return UserRoleEnum.ADMIN;
		}
		return UserRoleEnum.USER;
	}

	void verifyIfDuplicated(String name, String password) {
		Optional<User> checkUsername = userRepository.findUserByNameAndPassword(name, password);
		if (checkUsername.isPresent()) {
			throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
		}
	}
}
