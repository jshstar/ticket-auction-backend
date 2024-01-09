package com.sparta.ticketauction.domain.admin.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.seat.request.SeatRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class SeatRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void 좌석_구역_실패_테스트() {
		// given
		String zone = "";
		SeatRequest seatRequest = new SeatRequest(zone, 100);

		// when
		Set<ConstraintViolation<SeatRequest>> violations = validator.validate(seatRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("좌석 구역 입력은 필수입니다.");
				});

	}

	@Test
	public void 좌석_개수_실패_테스트() {
		// given
		SeatRequest seatRequest = new SeatRequest("A", null);

		// when
		Set<ConstraintViolation<SeatRequest>> violations = validator.validate(seatRequest);

		// then
		assertThat(violations).isNotEmpty();
		assertThat(violations).hasSize(1);
		assertThat(
			violations
				.iterator()
				.next()
				.getMessage())
			.isEqualTo("구역당 좌석 개수는 필수입니다.");
	}

	@Test
	public void 좌석_toEntity_메서드_성공_응답_테스트() {
		// given
		String zone = "A";
		Integer zoneCountSeat = 100;
		Place place = Mockito.mock();
		SeatRequest seatRequest = new SeatRequest(zone, zoneCountSeat);

		// when
		Seat seat = seatRequest.toEntity(place, 1);

		// then
		assertEquals(zone, seat.getZone());
		assertEquals(1, seat.getSeatNumber());
	}

}
