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

import com.sparta.ticketauction.domain.goods.entity.AgeGrade;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.place.entity.Place;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class GoodsRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	String goodsName;

	String goodsDescription;

	LocalDate startDate;

	LocalDate endDate;

	Integer ageGrade;

	LocalTime startTime;

	Integer runningTime;

	String categoryName;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	@BeforeEach
	void createInit() {
		this.goodsName = "강아지 쇼케이스";
		this.goodsDescription = "강아지의 신나는 공연";
		this.startDate = LocalDate.of(2023, 3, 1);
		this.endDate = LocalDate.of(2023, 4, 1);
		this.ageGrade = 12;
		this.startTime = LocalTime.of(12, 30);
		this.runningTime = 360;
		this.categoryName = "공연";
	}

	@Test
	void 공연이름_검증_테스트() {
		// given
		GoodsRequest goodsRequest = new GoodsRequest(
			"",
			this.goodsDescription,
			this.startDate,
			this.endDate,
			this.ageGrade,
			this.startTime,
			this.runningTime,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsRequest>> violations = validator.validate(goodsRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("1~30자 사이로 입력해주세요");
				});

	}

	@Test
	void 공연내용_검증_테스트() {
		// given
		GoodsRequest goodsRequest = new GoodsRequest(
			this.goodsName,
			"",
			this.startDate,
			this.endDate,
			this.ageGrade,
			this.startTime,
			this.runningTime,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsRequest>> violations = validator.validate(goodsRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("1~150자 사이로 입력해주세요");
				});

	}

	@Test
	void 공연_시작날짜_검증_테스트() {
		// given
		GoodsRequest goodsRequest = new GoodsRequest(
			this.goodsName,
			this.goodsDescription,
			null,
			this.endDate,
			this.ageGrade,
			this.startTime,
			this.runningTime,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsRequest>> violations = validator.validate(goodsRequest);

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
		GoodsRequest goodsRequest = new GoodsRequest(
			this.goodsName,
			this.goodsDescription,
			this.startDate,
			null,
			this.ageGrade,
			this.startTime,
			this.runningTime,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsRequest>> violations = validator.validate(goodsRequest);

		// then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("공연 종료일은 필수입니다.");
				});

	}

	@Test
	void 공연_연령_검증_테스트() {
		// given
		GoodsRequest goodsRequest = new GoodsRequest(
			this.goodsName,
			this.goodsDescription,
			this.startDate,
			this.endDate,
			null,
			this.startTime,
			this.runningTime,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsRequest>> violations = validator.validate(goodsRequest);

		// then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("연령 입력은 필수입니다.");
				});

	}

	@Test
	void 공연_상영시간_검증_테스트() {
		// given
		GoodsRequest goodsRequest = new GoodsRequest(
			this.goodsName,
			this.goodsDescription,
			this.startDate,
			this.endDate,
			this.ageGrade,
			null,
			this.runningTime,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsRequest>> violations = validator.validate(goodsRequest);

		// then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("상영 시간은 필수입니다");
				});

	}

	@Test
	void 공연_공연시간_검증_테스트() {
		// given
		GoodsRequest goodsRequest = new GoodsRequest(
			this.goodsName,
			this.goodsDescription,
			this.startDate,
			this.endDate,
			this.ageGrade,
			this.startTime,
			null,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsRequest>> violations = validator.validate(goodsRequest);

		// then
		assertFalse(violations.isEmpty());
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("공연 시간은 필수입니다");
				});

	}

	@Test
	void 공연_카테고리_검증_테스트() {
		// given
		GoodsRequest goodsRequest = new GoodsRequest(
			this.goodsName,
			this.goodsDescription,
			this.startDate,
			this.endDate,
			this.ageGrade,
			this.startTime,
			this.runningTime,
			"");

		// when
		Set<ConstraintViolation<GoodsRequest>> violations = validator.validate(goodsRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("카테고리 입력은 필수입니다.");
				});

	}

	@Test
	void Entity_생성_성공_테스트() {
		// given
		Place place = Mockito.mock();
		GoodsRequest goodsRequest = new GoodsRequest(
			this.goodsName,
			this.goodsDescription,
			this.startDate,
			this.endDate,
			this.ageGrade,
			this.startTime,
			this.runningTime,
			this.categoryName);

		//when
		Goods goods = goodsRequest.toEntity(place);

		GoodsCategory goodsCategory =
			GoodsCategory
				.builder()
				.name(this.categoryName)
				.build();
		goods.updateGoodsCategory(goodsCategory);

		//then
		assertEquals(this.goodsName, goods.getName());
		assertEquals(this.goodsDescription, goods.getDescription());
		assertEquals(this.startDate, goods.getStartDate());
		assertEquals(this.endDate, goods.getEndDate());
		assertEquals(AgeGrade.AGE_12, goods.getAgeGrade());
		assertEquals(this.runningTime, goods.getRunningTime());
		assertEquals(this.categoryName, goods.getGoodsCategory().getName());

	}

}
