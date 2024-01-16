package com.sparta.ticketauction.domain.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
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

	public String[] login() {
		User user = createUser();

		String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getRole(),
			user.getNickname());
		String refreshToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail(), user.getRole(),
			user.getNickname());

		lettuceUtils.save(
			"RefreshToken " + user.getEmail(),
			jwtUtil.substringToken(refreshToken),
			REFRESH_TOKEN_EXPIRATION
		);

		return new String[] {accessToken, refreshToken};
	}

	public String adminLogin() {
		User user = createAdminUser();
		String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getRole(),
			user.getNickname());
		String refreshToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail(), user.getRole(),
			user.getNickname());

		lettuceUtils.save(
			"RefreshToken " + user.getEmail(),
			jwtUtil.substringToken(refreshToken),
			REFRESH_TOKEN_EXPIRATION
		);

		return accessToken;
	}

	public String getRole(String token) {
		Claims claims = jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(token));

		return (String)claims.get("auth");

	}

	public void deleteToken(String username) {
		lettuceUtils.delete("RefreshToken " + username);
	}
}
