package com.sparta.ticketauction.domain.admin.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.goods.entity.AgeGrade;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class GoodsInfoCreateRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	String goodsName;

	String goodsDescription;

	Integer ageGrade;

	Integer runningTime;

	String categoryName;

	GoodsInfoCreateRequest goodsInfoCreateRequest;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	@BeforeEach
	void createInit() {
		this.goodsName = "강아지 쇼케이스";
		this.goodsDescription = "강아지의 신나는 공연";
		this.ageGrade = 12;
		this.runningTime = 360;
		this.categoryName = "공연";
	}

	@Test
	void 공연이름_검증_테스트() {
		// given
		GoodsInfoCreateRequest goodsInfoCreateRequest = new GoodsInfoCreateRequest(
			"",
			this.goodsDescription,
			this.ageGrade,
			this.runningTime,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsInfoCreateRequest>> violations = validator.validate(goodsInfoCreateRequest);

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
		GoodsInfoCreateRequest goodsInfoCreateRequest = new GoodsInfoCreateRequest(
			this.goodsName,
			"",
			this.ageGrade,
			this.runningTime,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsInfoCreateRequest>> violations = validator.validate(goodsInfoCreateRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("1~150자 사이로 입력해주세요");
				});

	}

	@Test
	void 공연_공연시간_검증_테스트() {
		// given
		GoodsInfoCreateRequest goodsInfoCreateRequest = new GoodsInfoCreateRequest(
			this.goodsName,
			this.goodsDescription,
			this.ageGrade,
			null,
			this.categoryName);

		// when
		Set<ConstraintViolation<GoodsInfoCreateRequest>> violations = validator.validate(goodsInfoCreateRequest);

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
		GoodsInfoCreateRequest goodsInfoCreateRequest = new GoodsInfoCreateRequest(
			this.goodsName,
			this.goodsDescription,
			this.ageGrade,
			this.runningTime,
			"");

		// when
		Set<ConstraintViolation<GoodsInfoCreateRequest>> violations = validator.validate(goodsInfoCreateRequest);

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
		GoodsInfoCreateRequest goodsInfoCreateRequest = new GoodsInfoCreateRequest(
			this.goodsName,
			this.goodsDescription,
			this.ageGrade,
			this.runningTime,
			this.categoryName);

		//when
		GoodsInfo goodsInfo = goodsInfoCreateRequest.toEntity();

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

}
