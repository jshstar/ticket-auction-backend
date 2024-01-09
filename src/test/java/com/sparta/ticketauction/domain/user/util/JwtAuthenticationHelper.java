package com.sparta.ticketauction.domain.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.domain.user.request.UserLoginRequest;
import com.sparta.ticketauction.global.jwt.JwtUtil;
import com.sparta.ticketauction.global.util.LettuceUtils;

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
		UserCreateRequest request = UserUtil.getUserCreateRequest();
		return userRepository.save(request.toEntity(passwordEncoder));

	}

	public String login() {
		UserLoginRequest request = UserLoginRequest.builder()
			.email(UserUtil.EMAIL)
			.password(UserUtil.PASSWORD)
			.build();

		User user = createUser();
		String accessToken = jwtUtil.createAccessToken(user.getEmail(), user.getRole());
		String refreshToken = jwtUtil.createRefreshToken(user.getEmail(), user.getRole());

		lettuceUtils.save(user.getEmail(), refreshToken, REFRESH_TOKEN_EXPIRATION);

		return accessToken;
	}
}
