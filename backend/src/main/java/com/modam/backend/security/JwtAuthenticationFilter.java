package com.modam.backend.security;

import com.modam.backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("JwtAuthenticationFilter doFilterInternal called for URL: " + request.getRequestURI());//디버그

        String path = request.getRequestURI();

        // 로그인, 회원가입 요청은 토큰 검사 없이 바로 통과
        if (path.equals("/user/login") || path.equals("/user/signup")) {
            filterChain.doFilter(request, response);
            return;
        }


        //JWT 처리 로직
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtUtil.validateToken(token)) {
                String userId = jwtUtil.getUserIdFromToken(token);

                // 간단히 인증 객체 생성 (권한 부여 등은 상황에 따라 확장)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(new SimpleGrantedAuthority("USER")));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("JWT 인증 성공, userId: " + userId);
            }
            else{
                System.out.println("JWT 토큰 검증 실패");
            }
        }
        else {
            System.out.println("Authorization 헤더 없음 또는 Bearer 토큰 아님");
        }
        filterChain.doFilter(request, response);
    }

    //예외처리 추가
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/user/login") ||
                path.startsWith("/user/signup") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/chat") ||
                path.startsWith("/ws"); // /chat/** 예외 추가
    }


}
