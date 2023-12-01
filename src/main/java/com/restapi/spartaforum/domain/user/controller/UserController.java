package com.restapi.spartaforum.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restapi.spartaforum.domain.user.dto.SignInRequestDTO;
import com.restapi.spartaforum.domain.user.dto.SignUpRequestDTO;
import com.restapi.spartaforum.domain.user.entity.UserServiceMessage;
import com.restapi.spartaforum.domain.user.service.KakaoService;
import com.restapi.spartaforum.domain.user.service.UserSignInService;
import com.restapi.spartaforum.domain.user.service.UserSignUpService;
import com.restapi.spartaforum.security.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
//@RequestMapping("/api/sparta-forum/user")
@Slf4j
public class UserController {

	private final UserSignUpService signUpService;

	private final KakaoService kakaoService;

	private final UserSignInService signInService;

	@PostMapping("/signup")
	public ResponseEntity<UserServiceMessage> signUp(@RequestBody @Valid SignUpRequestDTO requestDto,
		BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (fieldErrors.size() > 0) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				log.error(fieldError.getField() + "필드" + fieldError.getDefaultMessage());
			}
		}

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

	/**
	 * 인가코드 받기
	 *
	 * 레퍼런스
	 *
	 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-request-query -
	 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-response
	 * @param code
	 * @param response
	 * @return
	 *
	 * @throws JsonProcessingException
	 */
	@GetMapping("/kakao/callback")
	public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response)
		throws JsonProcessingException {
		log.info("=== Controller Starts ===");
		log.info("code : " + code);

		// 인가 코드를 통해 액세스 토큰 생성
		String token = kakaoService.kakaoLogin(code);
		log.info("Token has created : " + token);

		// 액세스 토큰을 쿠키에 저장
		Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7)); // 1-3강의 25:20 :: "Bearer " 떼기
		cookie.setPath("/");

		// response에 쿠키 저장
		response.addCookie(cookie);

		log.info("=== Controller Ends ===");
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
}
