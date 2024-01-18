package com.sparta.ticketauction.domain.user.service.impl;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.domain.user.request.UserDeleteRequest;
import com.sparta.ticketauction.domain.user.request.UserPasswordUpdateRequest;
import com.sparta.ticketauction.domain.user.request.UserUpdateRequest;
import com.sparta.ticketauction.domain.user.response.UserResponse;
import com.sparta.ticketauction.domain.user.service.UserService;
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

	@Transactional
	@Override
	public UserResponse signup(UserCreateRequest request) {
		String email = request.getEmail();
		String nickname = request.getNickname();

		/* 이메일 중복 검사 */
		if (userRepository.existsByEmailAndIsDeletedIsFalse(email)) {
			throw new ApiException(ErrorCode.EXISTED_USER_EMAIL);
		}

		/* 닉네임 중복 검사 */
		checkNickname(nickname);

		/* 전화 번호 인증 번호 검사 */
		checkPhoneVerificationCode(request.getPhoneNumber(), request.getVerificationNumber());

		User user = request.toEntity(passwordEncoder);
		userRepository.save(user);

		return UserResponse.from(user);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isExistedPhoneNumber(String phoneNumber) {
		return userRepository.existsByPhoneNumberAndIsDeletedIsFalse(phoneNumber);

	}

	@Override
	@Transactional(readOnly = true)
	public User findByUserId(Long userId) {
		return userRepository.findByIdAndIsDeletedIsFalse(userId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_BY_ID));
	}

	@Transactional
	@Override
	public UserResponse updateUserInfo(User loginUser, Long userId, UserUpdateRequest request) {
		User user = checkAndGetUser(loginUser, userId);

		if (!request.getNickname().isBlank()) {
			if (request.getNickname().length() < 2 || request.getNickname().length() > 10) {
				throw new ApiException(INVALID_NICKNAME_LENGTH);
			}
			String nicknameRegexp = "^[가-힣]+$";
			if (!Pattern.matches(nicknameRegexp, request.getNickname())) {
				throw new ApiException(INVALID_NICKNAME_PATTERN);
			}
			checkNickname(request.getNickname());
			user.updateUserNickName(request.getNickname());
		}

		if (!request.getPhoneNumber().isBlank()) {
			String phoneRegexp = "^01([0|1|6|7|8|9])?([0-9]{3,4})?([0-9]{4})$";
			if (!Pattern.matches(phoneRegexp, request.getPhoneNumber())) {
				throw new ApiException(INVALID_PHONE_NUMBER_PATTERN);
			}
			checkPhoneVerificationCode(request.getPhoneNumber(), request.getVerificationNumber());
			user.updatePhoneNumber(request.getPhoneNumber());
		}
		return UserResponse.from(user);
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse getUserInfo(User loginUser, Long userId) {
		User user = checkAndGetUser(loginUser, userId);

		return UserResponse.from(user);
	}

	@Override
	@Transactional
	public void updateUserPassword(User loginUser, Long userId, UserPasswordUpdateRequest request) {
		User user = checkAndGetUser(loginUser, userId);

		checkPassword(user, request.getPassword());
		user.updatePassword(passwordEncoder.encode(request.getPassword()));
	}

	@Override
	@Transactional
	public void deleteUser(User loginUser, UserDeleteRequest request) {
		User user = findByUserId(loginUser.getId());
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new ApiException(NOT_MATCH_PASSWORD);
		}
		user.delete();
	}

	@Override
	@Transactional(readOnly = true)
	public Long findUserPoint(Long userId) {
		return userRepository.findPointById(userId);
	}

	private void checkPassword(User user, String password) {
		if (passwordEncoder.matches(password, user.getPassword())) {
			throw new ApiException(ALREADY_USED_PASSWORD);
		}
	}

	private void checkPhoneVerificationCode(String phoneNumber, String verificationCode) {
		/* 핸드폰 번호 인증 번호 검사 */
		if (!lettuceUtils.hasKey("[Verification]" + phoneNumber)) {
			throw new ApiException(EXCEED_VERIFICATION_TIME);
		}

		if (!lettuceUtils.get("[Verification]" + phoneNumber).equals(verificationCode)) {
			throw new ApiException(INVALID_VERIFICATION_NUMBER);
		}
	}

	private User checkAndGetUser(User loginUser, Long userId) {
		if (!loginUser.getId().equals(userId)) {
			throw new ApiException(ACCESS_DENIED);
		}
		return findByUserId(userId);
	}

	private void checkNickname(String nickname) {
		if (userRepository.existsByNicknameAndIsDeletedIsFalse(nickname)) {
			throw new ApiException(ErrorCode.EXISTED_USER_NICKNAME);
		}
	}
}
