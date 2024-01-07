package com.sparta.ticketauction.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void signup(UserCreateRequest request) {
		String email = request.getEmail();
		String nickname = request.getNickname();

		/* 이메일 중복 검사 */
		if (userRepository.existsByEmail(email)) {
			throw new ApiException(ErrorCode.EXISTED_USER_EMAIL);
		}

		/* 닉네임 중복 검사 */
		if (userRepository.existsByNickname(nickname)) {
			throw new ApiException(ErrorCode.EXISTED_USER_NICKNAME);
		}

		User user = User.of(request, passwordEncoder);
		userRepository.save(user);
	}
}
