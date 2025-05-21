package com.modam.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // 평문 비번 그대로 사용
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // REST API는 보통 csrf 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/signup").permitAll() // 로그인, 회원가입은 허용
                        .anyRequest().authenticated() // 나머지는 인증 필요
                );

        return http.build();
    }
}