package com.restapi.spartaforum.config;

import com.restapi.spartaforum.security.jwt.JwtAuthenticationFilter;
import com.restapi.spartaforum.security.jwt.JwtAuthorizationFilter;
import com.restapi.spartaforum.security.jwt.JwtUtil;
import com.restapi.spartaforum.security.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtUtil jwtUtil;

	private final UserDetailsServiceImpl userDetailsService;

	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 설정
		http.csrf(AbstractHttpConfigurer::disable);

		// 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
		http.sessionManagement((sessionManagement) ->
			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);

		// permit sources
		http.authorizeHttpRequests((authorizeHttpRequests) ->
				/*  더 구체적인 기준이 덜 구제적인 기준보다 우선하는 것이 절대적으로 중요하다.  */
				authorizeHttpRequests
					.anyRequest()
					.permitAll()
//                        .requestMatchers("/api/sparta-forum/admin/**")
//                        .hasRole("ADMIN") // '/api/sparta-forum/admin/**' 요청 ADMIN만 접근허용
//                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/sparta-forum/boards"))
//                        .permitAll()      // GET '/api/sparta-forum/boards' 요청"만" 모든 접근 허용
//                        .requestMatchers("/api/sparta-forum/user/**")
//                        .permitAll()      // '/api/sparta-forum/user/**' 요청 모든 접근 허용
//                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                        .permitAll()      // static resources 접근 허용 설정
//                        .anyRequest().authenticated()
		);

		// handle login
		http.formLogin((formLogin) ->
				formLogin.loginPage("/login")
					.permitAll()
//                        .loginProcessingUrl("/api/sparta-forum/user/signin")
//                        .defaultSuccessUrl("/")
//                        .failureUrl("/api/sparta-forum/user/login?error")
//                        .permitAll()
		);

		// 필터 관리
		http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// passwordEncoder
	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
		filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		return filter;
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
	}
}
