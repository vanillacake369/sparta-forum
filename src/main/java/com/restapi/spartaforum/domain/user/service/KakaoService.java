package com.restapi.spartaforum.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.spartaforum.domain.user.repository.UserRepository;
import com.restapi.spartaforum.domain.user.dto.KakaoUserInfoDto;
import com.restapi.spartaforum.security.jwt.JwtUtil;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	private final RestTemplate restTemplate;

	private final JwtUtil jwtUtil;

	// 1. 인가코드를 전달받음
	public String kakaoLogin(String code) throws JsonProcessingException {
		log.info("=== Service Starts ===");
		// 2. 인가 코드로 액세스 토큰 가져오기 (feat. restTemplate)
		String accessToken = getToken(code);
		log.info("accessToken : " + accessToken);

		// 3. 토큰으로 카카오 API 호출
		KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);
		log.info("user info : \n" + kakaoUserInfoDto.toString());

		return null;
	}

	private String getToken(String code) throws JsonProcessingException {
		/*  요청 URL 생성   */
		URI uri = UriComponentsBuilder
			.fromUriString("https://kauth.kakao.com") // kakao auth
			.path("/oauth/token")
			.encode()
			.build()
			.toUri();

		/*  HTTP header 생성  */
		HttpHeaders headers = new HttpHeaders();
		// x-www-form-urlencoded?? 이건 뭐지???
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		/*  HTTP Body 생성    */
		// Map<K, List<V>> 형식의 body
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "af8f5c25662a88d2f3c1eadcdcf8207a");
		body.add("redirect_uri", "http://localhost:8080/api/user/kakao/callback");
		// 인가코드
		body.add("code", code);

		RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
			.post(uri)
			.headers(headers)
			.body(body);

		// HTTP 요청 보내기
		ResponseEntity<String> response = restTemplate.exchange(
			requestEntity,
			String.class
		);

		// HTTP 응답 (JSON) -> 액세스 토큰 파싱
		JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
		return jsonNode.get("access_token").asText();
	}

	private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
		// 요청 URL 만들기
		URI uri = UriComponentsBuilder
			.fromUriString("https://kapi.kakao.com")
			.path("/v2/user/me")
			.encode()
			.build()
			.toUri();

		// HTTP Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
			.post(uri)
			.headers(headers)
			.body(new LinkedMultiValueMap<>());

		// HTTP 요청 보내기
		ResponseEntity<String> response = restTemplate.exchange(
			requestEntity,
			String.class
		);

		JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
		Long id = jsonNode.get("id").asLong();
		String nickname = jsonNode.get("properties")
			.get("nickname").asText();
//		String email = jsonNode.get("kakao_account")
//			.get("email").asText();
		String email = "randEmail";

		log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
		return new KakaoUserInfoDto(id, nickname, email);
	}
}
