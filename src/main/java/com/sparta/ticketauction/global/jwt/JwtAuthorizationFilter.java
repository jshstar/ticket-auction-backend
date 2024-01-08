package com.sparta.ticketauction.global.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.ticketauction.global.util.LettuceUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 토큰 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final LettuceUtils lettuceUtils;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = jwtUtil.resolveAccessToken(request);

		// 엑세스 토큰 검증
		if (StringUtils.hasText(accessToken) && jwtUtil.validateToken(accessToken)) {
			String username = jwtUtil.getUsernameFromToken(accessToken);
			String logoutToken = lettuceUtils.get("Logout: " + username);

			// 로그아웃 토큰 검증
			if (!StringUtils.hasText(logoutToken) || !accessToken.equals(logoutToken)) {
				setAuthentication(username);
			}
		}

		filterChain.doFilter(request, response);
	}

	/*
	 * 인증 처리하기
	 * */
	private void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	private Authentication createAuthentication(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(
			userDetails, userDetails.getPassword(), userDetails.getAuthorities(
		));
	}
}
