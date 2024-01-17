package com.sparta.ticketauction.domain.admin.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.place.entity.Place;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class GoodsCreateRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	String title;

	LocalDate startDate;

	LocalDate endDate;

	LocalTime startTime;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	@BeforeEach
	void createInit() {
		this.title = "레미제라블-서울";
		this.startDate = LocalDate.of(2023, 3, 1);
		this.endDate = LocalDate.of(2023, 4, 1);
		this.startTime = LocalTime.of(12, 30);
	}

	@Test
	void 공연_타이틀_빈공간_검증_테스트() {
		// given
		GoodsCreateRequest goodsCreateRequest = new GoodsCreateRequest(
			null,
			this.startDate,
			this.endDate,
			this.startTime
		);

		//when
		Set<ConstraintViolation<GoodsCreateRequest>> violations = validator.validate(goodsCreateRequest);

		//then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("공연제목 기입은 필수 입니다.");
				});

	}

	@Test
	void 공연_타이틀_패턴_검증_테스트() {
		// given
		GoodsCreateRequest goodsCreateRequest = new GoodsCreateRequest(
			"레미제라블서울",
			this.startDate,
			this.endDate,
			this.startTime
		);

		//when
		Set<ConstraintViolation<GoodsCreateRequest>> violations = validator.validate(goodsCreateRequest);

		//then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("입력 양식: (공연제목) - (지역 및 목표) 입니다.");
				});

	}

	@Test
	void 공연_시작날짜_검증_테스트() {
		// given
		GoodsCreateRequest goodsCreateRequest = new GoodsCreateRequest(
			this.title,
			null,
			this.endDate,
			this.startTime
		);

		// when
		Set<ConstraintViolation<GoodsCreateRequest>> violations = validator.validate(goodsCreateRequest);

		// then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("공연 시작일 기입은 필수입니다.");
				});

	}

	@Test
	void 공연_종료날짜_검증_테스트() {
		// given
		GoodsCreateRequest goodsCreateRequest = new GoodsCreateRequest(
			this.title,
			this.startDate,
			null,
			this.startTime
		);

		// when
		Set<ConstraintViolation<GoodsCreateRequest>> violations = validator.validate(goodsCreateRequest);

		// then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("공연 종료일은 필수입니다.");
				});

	}

	@Test
	void 공연_상연시간_검증_테스트() {
		// given
		GoodsCreateRequest goodsCreateRequest = new GoodsCreateRequest(
			this.title,
			this.startDate,
			this.endDate,
			null
		);

		// when
		Set<ConstraintViolation<GoodsCreateRequest>> violations = validator.validate(goodsCreateRequest);

		// then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("상영 시간은 필수입니다");
				});

	}

	@Test
	void 공연_Entity_생성_성공_테스트() {
		// given
		GoodsCreateRequest goodsCreateRequest = new GoodsCreateRequest(
			this.title,
			this.startDate,
			this.endDate,
			this.startTime
		);

		//when
		Place place = Mockito.mock();
		GoodsInfo goodsInfo = Mockito.mock();
		Goods goods = goodsCreateRequest.toEntity(place, goodsInfo);

		//then
		assertEquals(this.title, goods.getTitle());
		assertEquals(this.startDate.getYear(), goods.getStartDate().getYear());
		assertEquals(this.startDate.getMonth(), goods.getStartDate().getMonth());
		assertEquals(this.startDate.getDayOfMonth(), goods.getStartDate().getDayOfMonth());
		assertEquals(this.endDate.getYear(), goods.getEndDate().getYear());
		assertEquals(this.endDate.getMonth(), goods.getEndDate().getMonth());
		assertEquals(this.endDate.getDayOfMonth(), goods.getEndDate().getDayOfMonth());
		assertEquals(goodsInfo, goods.getGoodsInfo());
		assertEquals(place, goods.getPlace());
	}
}
