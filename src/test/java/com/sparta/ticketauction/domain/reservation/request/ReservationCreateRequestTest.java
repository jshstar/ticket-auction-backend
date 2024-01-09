package com.sparta.ticketauction.domain.reservation.request;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sparta.ticketauction.domain.reservation.ReservationUtil;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class ReservationCreateRequestTest {

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
	void 성공() {
		// When
		Set<ConstraintViolation<ReservationCreateRequest>> validations =
			validator.validate(ReservationUtil.TEST_RESERVATION_CREATE_REQUEST);

		// Then
		assertThat(validations).isEmpty();
	}

	@Nested
	class 구역_검증 {
		@Test
		void 실패_NULL_입력() throws Exception {
			// Given
			ReservationCreateRequest request = new ReservationCreateRequest(null, 100L);

			// When
			Set<ConstraintViolation<ReservationCreateRequest>> validations =
				validator.validateProperty(request, "zone");

			// Then
			assertThat(validations).isNotEmpty();
		}

		@Test
		void 실패_빈_값_입력() throws Exception {
			// Given
			ReservationCreateRequest request = new ReservationCreateRequest("", 100L);

			// When
			Set<ConstraintViolation<ReservationCreateRequest>> validations =
				validator.validateProperty(request, "zone");

			// Then
			assertThat(validations).isNotEmpty();
		}

		@Test
		void 실패_공백_입력() throws Exception {
			// Given
			ReservationCreateRequest request = new ReservationCreateRequest(" ", 100L);

			// When
			Set<ConstraintViolation<ReservationCreateRequest>> validations =
				validator.validateProperty(request, "zone");

			// Then
			assertThat(validations).isNotEmpty();
		}
	}

	@Nested
	class 가격_검증 {

		@Test
		void 실패_NULL_입력() throws Exception {
			// Given
			ReservationCreateRequest request = new ReservationCreateRequest("A", null);

			// When
			Set<ConstraintViolation<ReservationCreateRequest>> validations =
				validator.validateProperty(request, "price");

			// Then
			assertThat(validations).isNotEmpty();
		}
	}
}
