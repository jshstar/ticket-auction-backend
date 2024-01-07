package com.sparta.ticketauction.domain.user.request;

import static com.sparta.ticketauction.domain.user.UserUtil.*;
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

	@Test
	@DisplayName("검증 성공")
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

		// When
		Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

		// Then
		assertThat(validations).isEmpty();
	}

	@Nested
	@DisplayName("Email 검증")
	class EmailValid {

		@Test
		@DisplayName("이메일 형식이 아닐 때, 실패")
		void givenNonEmailType_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
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
			UserCreateRequest request = getUserCreateRequest(
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

	}

	@Nested
	@DisplayName("Password 검증")
	class PasswordValid {

		@Test
		@DisplayName("비밀 번호 길이가 8자 미만일 때, 실패")
		void givenUnder8Len_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				"a1!",
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
		@DisplayName("비밀 번호 길이가 15자 초과일 때, 실패")
		void givenOver15Len_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				"abcd1234!@#$1111",
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
		@DisplayName("비밀 번호에 영어가 포함되지 않을 때, 실패")
		void givenNonAlphabet_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				"1234!@#$1111",
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
		@DisplayName("비밀 번호에 특수 문자가 포함되지 않을 때, 실패")
		void givenNonSpecialCharacter_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				"1234abc1111",
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
		@DisplayName("비밀 번호에 한글이 포함될 때, 실패")
		void givenKorean_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				"abc123!@#ㄱㄴ",
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
	}

	@Nested
	@DisplayName("Name 검증")
	class NameValid {

		@Test
		@DisplayName("영어로 입력이 주어질 때, 실패")
		void givenNonEmailType_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				"name",
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
		@DisplayName("2자 미만 으로 주어질 때, 실패")
		void givenUnder2Len_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				"김",
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
		@DisplayName("입력이 10자를 초과할 때, 실패")
		void givenOver10Len_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				"김수한무두루미와거북이",
				NICKNAME,
				PHONE_NUMBER,
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}
	}

	@Nested
	@DisplayName("Nickname 검증")
	class NicknameValid {

		@Test
		@DisplayName("영어로 입력이 주어질 때, 실패")
		void givenNonEmailType_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NAME,
				"nickname",
				PHONE_NUMBER,
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}

		@Test
		@DisplayName("2자 미만 으로 주어질 때, 실패")
		void givenUnder2Len_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NAME,
				"닉",
				PHONE_NUMBER,
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}

		@Test
		@DisplayName("입력이 10자를 초과할 때, 실패")
		void givenOver10Len_fail() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NAME,
				"김수한무두루미와거북이",
				PHONE_NUMBER,
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}
	}

	@Nested
	@DisplayName("PhoneNumber 검증")
	class PhoneNumberValid {

		@Test
		@DisplayName("정해진 형식이 아닐 때, 실패")
		void givenNonPhoneType() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NICKNAME,
				NAME,
				"01012345678",
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}

		@Test
		@DisplayName("숫자와 - 외의 다른 문자가 입력될 때, 실패")
		void givenAnotherCharactersType() {
			// Given
			UserCreateRequest request = getUserCreateRequest(
				EMAIL,
				PASSWORD,
				NICKNAME,
				NAME,
				"asdㄴㅇㄴ",
				BIRTH
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}
	}
}