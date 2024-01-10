package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;
import com.sparta.ticketauction.global.util.LettuceUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final LettuceUtils lettuceUtils;

	@Override
	public void signup(UserCreateRequest request) {
		String email = request.getEmail();
		String nickname = request.getNickname();

		/* 이메일 중복 검사 */
		if (userRepository.existsByEmailAndIsDeletedIsFalse(email)) {
			throw new ApiException(ErrorCode.EXISTED_USER_EMAIL);
		}

		/* 닉네임 중복 검사 */
		if (userRepository.existsByNicknameAndIsDeletedIsFalse(nickname)) {
			throw new ApiException(ErrorCode.EXISTED_USER_NICKNAME);
		}

		/* 핸드폰 번호 인증 번호 검사 */
		if (!lettuceUtils.get("[Verification]" + request.getPhoneNumber())
			.equals(request.getVerificationNumber())
		) {
			throw new ApiException(INVALID_VERIFICATION_NUMBER);
		}

		User user = request.toEntity(passwordEncoder);
		userRepository.save(user);
	}

	@Override
	public boolean isExistedPhoneNumber(String phoneNumber) {
		return userRepository.existsByPhoneNumberAndIsDeletedIsFalse(phoneNumber);
	}

}
