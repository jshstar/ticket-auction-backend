package com.sparta.ticketauction.domain.admin.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class PlaceCreateRequestTest {

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
		List<ZoneInfo> zoneInfos = Mockito.mock();
		PlaceCreateRequest placeCreateRequest = new PlaceCreateRequest(name, address, zoneInfos);

		// when
		Place place = placeCreateRequest.toEntity(650);

		// then
		assertEquals(name, place.getName());
		assertEquals(address, place.getAddress());
		assertEquals(650, place.getCountSeats());
	}

	@Test
	public void 요청_공연장_이름_검증_실패테스트() {
		// given
		String invalidName = "";
		List<ZoneInfo> seatRequestList = new ArrayList<>();
		seatRequestList.add(new ZoneInfo("A", 100));
		PlaceCreateRequest placeCreateRequest = new PlaceCreateRequest(invalidName, "Valid Address", seatRequestList);

		// when
		Set<ConstraintViolation<PlaceCreateRequest>> violations = validator.validate(placeCreateRequest);

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
		List<ZoneInfo> seatRequestList = new ArrayList<>();
		seatRequestList.add(new ZoneInfo("A", 100));
		PlaceCreateRequest placeCreateRequest = new PlaceCreateRequest("예술의 전당", invalidAddress, seatRequestList);

		//when
		Set<ConstraintViolation<PlaceCreateRequest>> violations = validator.validate(placeCreateRequest);

		//then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("주소 입력은 필수입니다.");
				});

	}

	@Test
	public void 요청_공연장_구역_검증_실패테스트() {
		// given
		List<ZoneInfo> zoneInfos = new ArrayList<>();
		PlaceCreateRequest placeCreateRequest = new PlaceCreateRequest("예술의 전당", "Valid Address", zoneInfos);

		//when
		Set<ConstraintViolation<PlaceCreateRequest>> violations = validator.validate(placeCreateRequest);

		//then
		assertThat(violations).isEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("구역 정보 입력은 필수입니다.");
				});

	}

	@Test
	public void 공연장_요청_구역_정보_검증_실패테스트() {
		// given
		List<ZoneInfo> seatRequestList =
			new ArrayList<>(
				Arrays.asList(
					new ZoneInfo("", 1),
					new ZoneInfo("B", 1)
				)
			);

		//when
		Set<ConstraintViolation<List<ZoneInfo>>> violations = validator.validate(seatRequestList);

		//then
		assertThat(violations).isEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("구역 입력은 필수 입니다.");
				});

	}

	@Test
	public void 공연장_요청_구역_좌석_정보_검증_실패테스트() {
		// given
		List<ZoneInfo> zoneInfos =
			new ArrayList<>(
				Arrays.asList(
					new ZoneInfo("A", null),
					new ZoneInfo("B", 1)
				)
			);

		//when
		Set<ConstraintViolation<List<ZoneInfo>>> violations = validator.validate(zoneInfos);

		//then
		assertThat(violations).isEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("구역당 총 좌석입력은 필 수 입니다.");
				});

	}
}
