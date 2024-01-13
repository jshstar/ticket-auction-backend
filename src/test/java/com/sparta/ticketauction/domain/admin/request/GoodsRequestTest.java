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
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
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

	GoodsRequest goodsRequest;

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
	void 공연정보_Entity_생성_성공_테스트() {
		// given
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
		GoodsInfo goodsInfo = goodsRequest.toEntity();

		GoodsCategory goodsCategory =
			GoodsCategory
				.builder()
				.name(this.categoryName)
				.build();
		goodsInfo.updateGoodsCategory(goodsCategory);

		//then
		assertEquals(this.goodsName, goodsInfo.getName());
		assertEquals(this.goodsDescription, goodsInfo.getDescription());
		assertEquals(AgeGrade.AGE_12, goodsInfo.getAgeGrade());
		assertEquals(this.runningTime, goodsInfo.getRunningTime());
		assertEquals(this.categoryName, goodsInfo.getGoodsCategory().getName());

	}

	void 공연_Entity_생성_성공_테스트() {
		// given
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
		GoodsInfo goodsInfo = goodsRequest.toEntity();

		GoodsCategory goodsCategory =
			GoodsCategory
				.builder()
				.name(this.categoryName)
				.build();
		goodsInfo.updateGoodsCategory(goodsCategory);

		Place place = Mockito.mock();
		Goods goods = goodsRequest.toEntity(place, goodsInfo);

		//then
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
