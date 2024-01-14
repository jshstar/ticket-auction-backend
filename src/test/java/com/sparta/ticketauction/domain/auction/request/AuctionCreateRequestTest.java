package com.sparta.ticketauction.domain.auction.request;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
class AuctionCreateRequestTest {
	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	@Test
	void 경매_좌석_번호_검증() {
		AuctionCreateRequest request = new AuctionCreateRequest(
			null
		);

		//when
		var violations = validator.validate(request);

		//then
		assertThat(violations).isNotEmpty();
		violations.forEach(
			violation ->
				assertThat(violation.getMessage()).isEqualTo("경매 좌석 번호를 입력해주세요.")
		);

	}

	@Test
	void 시작일시_포맷_검증() {
		AuctionCreateRequest request = new AuctionCreateRequest(
			1
		);

		Schedule schedule = Schedule.builder()
			.startDateTime(LocalDateTime.now())
			.build();

		ZoneGrade zoneGrade = ZoneGrade.builder()
			.grade(Grade.builder().auctionPrice(1000L).normalPrice(10000L).build())
			.build();
		//when
		var auction = request.toEntity(schedule, zoneGrade);

		//then
		//yyyy-MM-dd HH:00
		var startDateTime = auction.getStartDateTime();
		var now = LocalDateTime.now();

		assertThat(startDateTime.getYear()).isEqualTo(now.getYear());
		assertThat(startDateTime.getMonthValue()).isEqualTo(now.getMonthValue());
		assertThat(startDateTime.getDayOfMonth()).isEqualTo(now.getDayOfMonth());
		assertThat(startDateTime.getHour()).isEqualTo(now.getHour());
		assertThat(startDateTime.getMinute()).isEqualTo(0);
	}

	@Test
	void 마감일시_포맷_검증() {
		AuctionCreateRequest request = new AuctionCreateRequest(
			1
		);

		Schedule schedule = Schedule.builder()
			.startDateTime(LocalDateTime.now())
			.build();

		ZoneGrade zoneGrade = ZoneGrade.builder()
			.grade(Grade.builder().auctionPrice(1000L).normalPrice(10000L).build())
			.build();
		//when
		var auction = request.toEntity(schedule, zoneGrade);

		//then
		//yyyy-MM-dd HH:00
		var endDateTime = auction.getEndDateTime();
		var now = LocalDateTime.now();

		assertThat(endDateTime.getDayOfMonth()).isEqualTo(now.getDayOfMonth() - 3);
	}
}