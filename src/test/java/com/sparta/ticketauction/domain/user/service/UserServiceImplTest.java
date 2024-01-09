package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.domain.user.util.UserUtil.*;
import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
import com.sparta.ticketauction.global.exception.ApiException;

@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;
	@Mock
	PasswordEncoder passwordEncoder;
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
				EMAIL,
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
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
				EMAIL,
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
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
		void 회원가입_성공() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
			);

			User user = request.toEntity(passwordEncoder);

			given(userRepository.existsByEmailAndIsDeletedIsFalse(request.getEmail())).willReturn(false);
			given(userRepository.existsByNicknameAndIsDeletedIsFalse(request.getNickname())).willReturn(false);
			given(userRepository.save(any(User.class))).willReturn(user);

			// When
			sut.signup(request);

			// Then
			verify(userRepository).existsByEmailAndIsDeletedIsFalse(request.getEmail());
			verify(userRepository).existsByNicknameAndIsDeletedIsFalse(request.getNickname());
			verify(userRepository).save(any(User.class));

			verify(userRepository).save(argumentCaptor.capture());
			assertEquals(request.getNickname(), argumentCaptor.getValue().getNickname());
		}
	}
}