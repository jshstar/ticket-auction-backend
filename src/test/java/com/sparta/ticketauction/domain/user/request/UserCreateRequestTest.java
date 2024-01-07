package com.sparta.ticketauction.domain.user.request;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("회원 가입 요청 검증 테스트")
class UserCreateRequestTest {

	private static final String EMAIL = "tester@gmail.com";
	private static final String PASSWORD = "test123!@#";
	private static final String NAME = "김수한";
	private static final String NICKNAME = "두루미";
	private static final String PHONE_NUMBER = "010-1234-5678";
	private static final LocalDate BIRTH = LocalDate.of(1990, 1, 1);
	private static ValidatorFactory validatorFactory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@AfterAll
	public static void close() {
		validatorFactory.close();
	}

	@Nested
	@DisplayName("Email 검증")
	class EmailValid {

		@Test
		@DisplayName("이메일 형식이 아닐 때, 실패")
		void givenNonEmailType_fail() {
			// Given
			UserCreateRequest request = new UserCreateRequest(
				"eeeee",
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}

		@Test
		@DisplayName("이메일이 빈 값으로 주어질 때, 실패")
		void givenEmptyEmailValue_fail() {
			// Given
			UserCreateRequest request = new UserCreateRequest(
				"",
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}

		@Test
		@DisplayName("이메일 검증 성공")
		void success() {
			// Given
			UserCreateRequest request = new UserCreateRequest(
				EMAIL,
				PASSWORD,
				NAME,
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).isEmpty();
		}
	}
}