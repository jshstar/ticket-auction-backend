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

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class GradeCreateRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	String name;

	Long normalPrice;

	Long auctionPrice;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	@BeforeEach
	void createInit() {
		this.name = "VIP";
		this.normalPrice = 100000L;
		this.auctionPrice = 70000L;

	}

	@Test
	void 등급_이름_검증_테스트() {
		// given
		GradeCreateRequest gradeCreateRequest =
			new GradeCreateRequest(
				"",
				this.normalPrice,
				this.auctionPrice
			);

		// when
		Set<ConstraintViolation<GradeCreateRequest>> violations = validator.validate(gradeCreateRequest);

		// then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("1~30자 사이로 입력해주세요.");
				});
	}

	@Test
	void 일반가격_검증_테스트() {
		// given
		GradeCreateRequest gradeCreateRequest =
			new GradeCreateRequest(
				this.name,
				null,
				this.auctionPrice
			);

		// when
		Set<ConstraintViolation<GradeCreateRequest>> violations = validator.validate(gradeCreateRequest);

		// then
		assertThat(violations).isNotNull();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("일반 가격 정보는 필수 입니다.");
				});
	}

	@Test
	void 경매가격_검증_테스트() {
		// given
		GradeCreateRequest gradeCreateRequest =
			new GradeCreateRequest(
				this.name,
				this.normalPrice,
				null
			);

		// when
		Set<ConstraintViolation<GradeCreateRequest>> violations = validator.validate(gradeCreateRequest);

		// then
		assertThat(violations).isNotNull();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("경매 가격 정보는 필수 입니다.");
				});
	}

	@Test
	void 등급_Entity_생성_테스트() {
		// given
		Goods goods = Mockito.mock();
		GradeCreateRequest gradeCreateRequest =
			new GradeCreateRequest(
				this.name,
				this.normalPrice,
				this.auctionPrice
			);

		// when
		Grade grade = gradeCreateRequest.toEntity(goods);

		// then
		assertEquals(this.name, grade.getName());
		assertEquals(this.normalPrice, grade.getNormalPrice());
		assertEquals(this.auctionPrice, grade.getAuctionPrice());
		assertEquals(goods, grade.getGoods());
	}

}
