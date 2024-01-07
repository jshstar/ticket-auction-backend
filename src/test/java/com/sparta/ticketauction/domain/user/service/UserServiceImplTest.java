package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.domain.user.UserUtil.*;
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

@DisplayName("User Service Test")
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
	@DisplayName("회원 가입 검증 테스트")
	class signupTest {

		@Test
		@DisplayName("실패 - 중복 이메일")
		void givenExistedEmail_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
			);

			given(userRepository.existsByEmail(request.getEmail())).willReturn(true);

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.signup(request)
			);

			// Then
			assertEquals(EXISTED_USER_EMAIL.getMessage(), exception.getMessage());
		}

		@Test
		@DisplayName("실패 - 중복 닉네임")
		void givenExistedNickname_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
			);

			given(userRepository.existsByEmail(request.getEmail())).willReturn(false);
			given(userRepository.existsByNickname(request.getNickname())).willReturn(true);

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.signup(request)
			);

			// Then
			assertEquals(EXISTED_USER_NICKNAME.getMessage(), exception.getMessage());
		}

		@Test
		@DisplayName("회원 가입 성공")
		void success() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
			);

			User user = User.of(request, passwordEncoder);

			given(userRepository.existsByEmail(request.getEmail())).willReturn(false);
			given(userRepository.existsByNickname(request.getNickname())).willReturn(false);
			given(userRepository.save(any(User.class))).willReturn(user);

			// When
			sut.signup(request);

			// Then
			verify(userRepository).existsByEmail(request.getEmail());
			verify(userRepository).existsByNickname(request.getNickname());
			verify(userRepository).save(any(User.class));

			verify(userRepository).save(argumentCaptor.capture());
			assertEquals(request.getNickname(), argumentCaptor.getValue().getNickname());
		}
	}
}