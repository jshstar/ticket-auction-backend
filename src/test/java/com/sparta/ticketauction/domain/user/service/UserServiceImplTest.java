package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.domain.user.util.UserUtil.*;
import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.domain.user.request.UserNicknameUpdateRequest;
import com.sparta.ticketauction.domain.user.request.UserPhoneUpdateRequest;
import com.sparta.ticketauction.domain.user.util.UserUtil;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.util.LettuceUtils;

@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	LettuceUtils lettuceUtils;
	@InjectMocks
	UserServiceImpl sut;
	@Captor
	ArgumentCaptor<User> argumentCaptor;

	@Nested
	class 회원_가입_검증_테스트 {

		@Test
		void 이미_존재하는_이메일인_경우_실패() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
			);

			given(userRepository.existsByEmailAndIsDeletedIsFalse(request.getEmail())).willReturn(true);

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.signup(request)
			);

			// Then
			assertEquals(EXISTED_USER_EMAIL.getMessage(), exception.getMessage());
		}

		@Test
		void 이미_존재하는_닉네임인_경우_실패() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
			);

			given(userRepository.existsByEmailAndIsDeletedIsFalse(request.getEmail())).willReturn(false);
			given(userRepository.existsByNicknameAndIsDeletedIsFalse(request.getNickname())).willReturn(true);

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.signup(request)
			);

			// Then
			assertEquals(EXISTED_USER_NICKNAME.getMessage(), exception.getMessage());
		}

		@Test
		void 인증번호_입력_시간_초과로_실패() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
			);

			given(userRepository.existsByEmailAndIsDeletedIsFalse(request.getEmail())).willReturn(false);
			given(userRepository.existsByNicknameAndIsDeletedIsFalse(request.getNickname())).willReturn(false);
			given(lettuceUtils.hasKey(any())).willReturn(false);

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.signup(request)
			);

			// Then
			assertThat(exception)
				.hasMessage(EXCEED_VERIFICATION_TIME.getMessage());
		}

		@Test
		void 인증번호_틀림으로_인한_실패() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
			);

			String verificationNumber = "111111";

			given(userRepository.existsByEmailAndIsDeletedIsFalse(request.getEmail())).willReturn(false);
			given(userRepository.existsByNicknameAndIsDeletedIsFalse(request.getNickname())).willReturn(false);
			given(lettuceUtils.hasKey(any())).willReturn(true);
			given(lettuceUtils.get(any())).willReturn(verificationNumber);

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.signup(request)
			);

			// Then
			assertThat(exception)
				.hasMessage(INVALID_VERIFICATION_NUMBER.getMessage());
		}

		@Test
		void 회원가입_성공() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
			);

			User user = request.toEntity(passwordEncoder);

			given(userRepository.existsByEmailAndIsDeletedIsFalse(request.getEmail())).willReturn(false);
			given(userRepository.existsByNicknameAndIsDeletedIsFalse(request.getNickname())).willReturn(false);
			given(lettuceUtils.hasKey(any())).willReturn(true);
			given(lettuceUtils.get(any())).willReturn(TEST_VERIFICATION_CODE);
			given(userRepository.save(any(User.class))).willReturn(user);

			// When
			sut.signup(request);

			// Then
			verify(userRepository).existsByEmailAndIsDeletedIsFalse(request.getEmail());
			verify(userRepository).existsByNicknameAndIsDeletedIsFalse(request.getNickname());
			verify(lettuceUtils).hasKey(any());
			verify(lettuceUtils).get(any());
			verify(userRepository).save(any(User.class));

			verify(userRepository).save(argumentCaptor.capture());
			assertEquals(request.getNickname(), argumentCaptor.getValue().getNickname());
		}
	}

	@Nested
	class 회원_닉네임_수정_테스트 {

		@Test
		void 성공() {
			// Given
			UserNicknameUpdateRequest request = UserNicknameUpdateRequest.builder().nickname(TEST_NICKNAME).build();
			User user = UserUtil.TEST_USER;

			given(userRepository.findByIdAndIsDeletedIsFalse(any())).willReturn(Optional.ofNullable(user));
			given(userRepository.existsByNicknameAndIsDeletedIsFalse(any())).willReturn(false);

			// When
			sut.updateUserNicknameInfo(user, 1L, request);

			// Then
			verify(userRepository).findByIdAndIsDeletedIsFalse(any());
			verify(userRepository).existsByNicknameAndIsDeletedIsFalse(any());
		}

		@Test
		void 이미_존재하는_닉네임으로_실패() {
			// Given
			UserNicknameUpdateRequest request = UserNicknameUpdateRequest.builder().nickname(TEST_NICKNAME).build();
			User user = UserUtil.TEST_USER;

			given(userRepository.findByIdAndIsDeletedIsFalse(any())).willReturn(Optional.ofNullable(user));
			given(userRepository.existsByNicknameAndIsDeletedIsFalse(any())).willReturn(true);

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.updateUserNicknameInfo(user, 1L, request)
			);

			// Then
			assertThat(exception)
				.hasMessage(EXISTED_USER_NICKNAME.getMessage());
		}

		@Test
		void 로그인한_유저에게_해당_수정_권한이_없어서_실패() {
			// Given
			UserNicknameUpdateRequest request = UserNicknameUpdateRequest.builder().nickname(TEST_NICKNAME).build();
			User user = UserUtil.TEST_USER;

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.updateUserNicknameInfo(user, 2L, request)
			);

			// Then
			assertThat(exception)
				.hasMessage(ACCESS_DENIED.getMessage());
		}
	}

	@Nested
	class 전화_번호_수정_테스트 {
		@Test
		void 성공() {
			// Given
			UserPhoneUpdateRequest request = new UserPhoneUpdateRequest(
				"01011111111",
				"123456"
			);

			User user = TEST_USER;

			given(userRepository.findByIdAndIsDeletedIsFalse(any())).willReturn(Optional.ofNullable(user));
			given(lettuceUtils.hasKey("[Verification]" + request.getPhoneNumber())).willReturn(true);
			given(lettuceUtils.get("[Verification]" + request.getPhoneNumber()))
				.willReturn(request.getVerificationNumber());

			// When
			sut.updateUserPhoneInfo(user, 1L, request);

			// Then
			verify(userRepository).findByIdAndIsDeletedIsFalse(any());
			verify(lettuceUtils).hasKey("[Verification]" + request.getPhoneNumber());
			verify(lettuceUtils).get("[Verification]" + request.getPhoneNumber());
		}

		@Test
		void 인증_번호_일치_실패로_인해_실패() {
			// Given
			UserPhoneUpdateRequest request = new UserPhoneUpdateRequest(
				"01011111111",
				"123456"
			);

			User user = TEST_USER;

			given(userRepository.findByIdAndIsDeletedIsFalse(any())).willReturn(Optional.ofNullable(user));
			given(lettuceUtils.hasKey("[Verification]" + request.getPhoneNumber())).willReturn(true);
			given(lettuceUtils.get("[Verification]" + request.getPhoneNumber()))
				.willReturn("111111");

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.updateUserPhoneInfo(user, 1L, request)
			);

			// Then
			assertThat(exception)
				.hasMessage(INVALID_VERIFICATION_NUMBER.getMessage());
		}

		@Test
		void 로그인한_유저에게_해당_수정_권한이_없어서_실패() {
			// Given
			UserPhoneUpdateRequest request = new UserPhoneUpdateRequest(
				"01011111111",
				"123456"
			);
			User user = UserUtil.TEST_USER;

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.updateUserPhoneInfo(user, 2L, request)
			);

			// Then
			assertThat(exception)
				.hasMessage(ACCESS_DENIED.getMessage());
		}
	}
}