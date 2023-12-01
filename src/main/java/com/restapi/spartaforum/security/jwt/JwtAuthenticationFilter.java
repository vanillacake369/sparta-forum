package com.restapi.spartaforum.security.jwt;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.spartaforum.domain.user.dto.SignInRequestDTO;
import com.restapi.spartaforum.domain.user.entity.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/api/sparta-forum/user/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
		log.info("로그인 시도");
		log.info("request.getParameter(\"username\") : " + request.getParameter("username"));
		log.info("request.getParameter(\"password\") : " + request.getParameter("password"));

		try {
			SignInRequestDTO requestDto = new ObjectMapper().configure(Feature.AUTO_CLOSE_SOURCE, true)
				.readValue(request.getInputStream(),
					SignInRequestDTO.class);
			log.error(requestDto.toString());

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					requestDto.nickName(),
					requestDto.password(),
					null
				)
			);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.info("로그인 성공 및 JWT 생성");
		String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
		UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

		String token = jwtUtil.createToken(username, role);
		jwtUtil.addJwtToCookie(token, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		log.info("로그인 실패");
		log.info("request.getParameter(username) : " + request.getParameter("username"));
		log.info("request.getParameter(password) : " + request.getParameter("password"));
		log.info("failed.getMessage() : " + failed.getMessage());
		response.setStatus(401);
	}
}