package com.sparta.ticketauction.domain.admin.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.seat.request.SeatRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class PlaceRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void toEntity_메서드_성공_응답_테스트() {
		// given
		String name = "예술의 전당";
		String address = "XX시 XX구";
		List<SeatRequest> seatRequestList = Mockito.mock();
		PlaceRequest placeRequest = new PlaceRequest(name, address, seatRequestList);

		// when
		Place place = placeRequest.toEntity(650);

		// then
		assertEquals(name, place.getName());
		assertEquals(address, place.getAddress());
		assertEquals(650, place.getCountSeats());
	}

	@Test
	public void 요청_공연장_이름_검증_실패테스트() {
		// given
		String invalidName = "";
		List<SeatRequest> seatRequestList = new ArrayList<>();
		seatRequestList.add(new SeatRequest("A", 100));
		PlaceRequest placeRequest = new PlaceRequest(invalidName, "Valid Address", seatRequestList);

		// when
		Set<ConstraintViolation<PlaceRequest>> violations = validator.validate(placeRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("공연장 이름은 필수입니다.");
				});

	}

	@Test
	public void 요청_공연장_주소_검증_실패테스트() {
		// given
		String invalidAddress = "";
		List<SeatRequest> seatRequestList = new ArrayList<>();
		seatRequestList.add(new SeatRequest("A", 100));
		PlaceRequest placeRequest = new PlaceRequest("예술의 전당", invalidAddress, seatRequestList);

		//when
		Set<ConstraintViolation<PlaceRequest>> violations = validator.validate(placeRequest);

		//then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("주소 입력은 필수입니다.");
				});

	}

	@Test
	public void 요청_공연장_좌석_검증_실패테스트() {
		// given
		List<SeatRequest> seatRequestList = new ArrayList<>();
		PlaceRequest placeRequest = new PlaceRequest("예술의 전당", "Valid Address", seatRequestList);

		//when
		Set<ConstraintViolation<PlaceRequest>> violations = validator.validate(placeRequest);

		//then
		assertThat(violations).isEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("좌석 정보는 필수입니다.");
				});

	}

}
