package com.sparta.ticketauction.global.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.global.security.UserDetailsImpl;
import com.sparta.ticketauction.global.util.LettuceUtils;

import io.jsonwebtoken.Claims;
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

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = jwtUtil.resolveAccessToken(request);

		// 엑세스 토큰 검증
		if (StringUtils.hasText(accessToken)) {
			jwtUtil.validateToken(accessToken);

			Claims info = jwtUtil.getUserInfoFromToken(accessToken);
			Long id = Long.parseLong(info.get("identify").toString());
			String username = info.getSubject();
			Role role = Role.valueOf((String)info.get("auth"));

			String logoutToken = lettuceUtils.get("Logout: " + username);
			// 로그아웃 토큰 검증
			if (!StringUtils.hasText(logoutToken) || !accessToken.equals(logoutToken)) {
				setAuthentication(id, username, role);
			}
		}
		filterChain.doFilter(request, response);
	}

	/*
	 * 인증 처리하기
	 * */
	private void setAuthentication(Long id, String username, Role role) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(id, username, role);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	private Authentication createAuthentication(Long id, String username, Role role) {
		UserDetails userDetails = new UserDetailsImpl(
			User.builder()
				.id(id)
				.email(username)
				.role(role)
				.build()
		);

		return new UsernamePasswordAuthenticationToken(
			userDetails, null, userDetails.getAuthorities()
		);
	}
}
