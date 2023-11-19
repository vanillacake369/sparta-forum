package com.restapi.spartaforum.config;

import com.restapi.spartaforum.config.jwt.JwtAuthenticationFilter;
import com.restapi.spartaforum.config.jwt.JwtAuthorizationFilter;
import com.restapi.spartaforum.config.jwt.JwtUtil;
import com.restapi.spartaforum.config.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // permit sources
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                /*  더 구체적인 기준이 덜 구제적인 기준보다 우선하는 것이 절대적으로 중요하다.  */
                authorizeHttpRequests
                        .requestMatchers("/api/sparta-forum/admin/**")
                        .hasRole("ADMIN") // '/api/sparta-forum/admin/**' 요청 ADMIN만 접근허용
                        .requestMatchers("/api/sparta-forum/user/**")
                        .permitAll() // '/api/sparta-forum/user/**' 요청 모든 접근 허용
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()) // static resources
                        .permitAll() // 지정한 resources 접근 허용 설정
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // handle login
        http.formLogin((formLogin) ->
                formLogin.loginPage("/api/sparta-forum/user/login-page").permitAll()
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
