package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;

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

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	private static final String EMAIL = "tester@gmail.com";
	private static final String PASSWORD = "test123!@#";
	private static final String NAME = "김수한";
	private static final String NICKNAME = "두루미";
	private static final String PHONE_NUMBER = "010-1234-5678";
	private static final LocalDate BIRTH = LocalDate.of(1990, 1, 1);
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
			UserCreateRequest request = new UserCreateRequest(
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

	}
}