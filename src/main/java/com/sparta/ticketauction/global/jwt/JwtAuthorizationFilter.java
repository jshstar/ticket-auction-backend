package com.sparta.ticketauction.global.jwt;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static com.sparta.ticketauction.global.jwt.JwtUtil.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.security.UserDetailsImpl;
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
		String refreshToken = jwtUtil.resolveRefreshToken(request);

		if (!StringUtils.hasText(accessToken)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 엑세스 토큰 검증
		jwtUtil.validateToken(accessToken);

		// 리프레시 토큰 검증
		if (!StringUtils.hasText(refreshToken) || jwtUtil.validateToken(refreshToken)) {
			throw new ApiException(INVALID_TOKEN);
		}

		// 로그 아웃 되어 있는지 검증
		if (!lettuceUtils.hasKey(refreshToken)) {
			throw new ApiException(REQUIRED_LOGIN);
		}

		// 리프레시 토큰이 만료되지 않았는데, 엑세스 토큰이 없다면 재발급하자
		response.addHeader(ACCESS_TOKEN_HEADER, reissueAccessToken(refreshToken));
		filterChain.doFilter(request, response);
	}

	private String reissueAccessToken(String refreshToken) {
		String username = lettuceUtils.get(refreshToken);
		UserDetailsImpl userDetails = (UserDetailsImpl)userDetailsService.loadUserByUsername(username);
		Role role = userDetails.getUser().getRole();
		String newAccessToken = jwtUtil.createAccessToken(username, role);

		setAuthentication(username);
		return newAccessToken;
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
