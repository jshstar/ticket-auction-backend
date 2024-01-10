package com.sparta.ticketauction.domain.user.request;

import static com.sparta.ticketauction.domain.user.util.UserUtil.*;
import static org.assertj.core.api.Assertions.*;

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
			TEST_EMAIL,
			TEST_PASSWORD,
			TEST_NAME,
			TEST_NICKNAME,
			TEST_PHONE_NUMBER,
			TEST_BIRTH,
			TEST_VERIFICATION_CODE
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
				TEST_PASSWORD,
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_PASSWORD,
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				"a1!",
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				"abcd1234!@#$1111",
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				"1234!@#$1111",
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				"1234abc1111",
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				"abc123!@#ㄱㄴ",
				TEST_NAME,
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				TEST_PASSWORD,
				"name",
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				TEST_PASSWORD,
				"김",
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				TEST_PASSWORD,
				"김수한무두루미와거북이",
				TEST_NICKNAME,
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				"nickname",
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				"닉",
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				"김수한무두루미와거북이",
				TEST_PHONE_NUMBER,
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NICKNAME,
				TEST_NAME,
				"010-1123-5678",
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
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
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NICKNAME,
				TEST_NAME,
				"asdㄴㅇㄴ",
				TEST_BIRTH,
				TEST_VERIFICATION_CODE
			);

			// When
			Set<ConstraintViolation<UserCreateRequest>> validations = validator.validate(request);

			// Then
			assertThat(validations).hasSize(1);
		}
	}
}