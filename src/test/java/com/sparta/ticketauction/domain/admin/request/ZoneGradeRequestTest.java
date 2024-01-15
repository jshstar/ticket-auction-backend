package com.sparta.ticketauction.domain.admin.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class ZoneGradeRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	Long zoneId;

	Long gradeId;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@BeforeEach
	public void createInit() {
		this.zoneId = 1L;
		this.gradeId = 1L;
	}

	@Test
	void 구역_아이디_검증_테스트() {
		// given
		ZoneGradeRequest zoneGradeRequest = new ZoneGradeRequest(null, this.gradeId);

		// when
		Set<ConstraintViolation<ZoneGradeRequest>> violations = validator.validate(zoneGradeRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("구역 Id값은 필수입니다.");
				});
	}

	@Test
	void 등급_아이디_검증_테스트() {
		// given
		ZoneGradeRequest zoneGradeRequest = new ZoneGradeRequest(this.zoneId, null);

		// when
		Set<ConstraintViolation<ZoneGradeRequest>> violations = validator.validate(zoneGradeRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("등급 Id값은 필수입니다.");
				});
	}

	@Test
	void 구역_등급_Entity_생성_테스트() {
		ZoneGradeRequest zoneGradeRequest = new ZoneGradeRequest(this.zoneId, this.gradeId);
		Place place = Mockito.mock();
		Goods goods = Mockito.mock();
		Zone zone =
			Zone
				.builder()
				.name("A")
				.place(place)
				.seatNumber(100)
				.build();

		Grade grade =
			Grade
				.builder()
				.name("VIP")
				.auctionPrice(70000L)
				.normalPrice(100000L)
				.goods(goods)
				.build();

		ZoneGrade zoneGrade = zoneGradeRequest.toEntity(zone, grade);

		// when
		assertEquals(zoneGrade.getZone(), zone);
		assertEquals(zoneGrade.getGrade(), grade);

	}

}
