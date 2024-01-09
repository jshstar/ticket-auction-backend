package com.sparta.ticketauction.domain.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.domain.user.request.UserLoginRequest;
import com.sparta.ticketauction.global.jwt.JwtUtil;
import com.sparta.ticketauction.global.util.LettuceUtils;

import io.jsonwebtoken.Claims;

@Component
public class JwtAuthenticationHelper {

	private static final int REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 30;

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private LettuceUtils lettuceUtils;

	public User createUser() {
		return userRepository.save(UserUtil.TEST_USER);
	}

	public User createAdminUser() {
		return userRepository.save(UserUtil.AMDIN_USER);
	}

	public String adminLogin() {
		UserLoginRequest request = UserLoginRequest.builder()
			.email(UserUtil.ADMIN_TEST_EMAIL)
			.password(UserUtil.ADMIN_TEST_PASSWORD)
			.build();

		User user = createAdminUser();
		String accessToken = jwtUtil.createAccessToken(user.getEmail(), user.getRole());
		String refreshToken = jwtUtil.createRefreshToken(user.getEmail(), user.getRole());

		lettuceUtils.save(user.getEmail(), refreshToken, REFRESH_TOKEN_EXPIRATION);

		return accessToken;
	}

	public Role getRole(String token) {
		Claims claims = jwtUtil.getUserInfoFromToken(token);
		
	}
}
