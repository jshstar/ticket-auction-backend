package com.sparta.ticketauction.domain.admin.request;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.SellType;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class GoodsSequenceSeatRequestTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	Long generalAuctionPrice;

	Long auctionPrice;

	String zone;

	List<Integer> auctionSeats = new ArrayList<>();

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	@BeforeEach
	void createInit() {
		this.generalAuctionPrice = 55000L;
		this.auctionPrice = 35000L;
		this.zone = "A";
		this.auctionSeats.add(1);
		this.auctionSeats.add(2);
		this.auctionSeats.add(3);

	}

	@Test
	void 일반_가격_검증_테스트() {
		// given
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
			= new GoodsSequenceSeatRequest(null, this.auctionPrice, this.zone, this.auctionSeats);

		// when
		Set<ConstraintViolation<GoodsSequenceSeatRequest>> violations = validator.validate(goodsSequenceSeatRequest);

		//then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("잘못된 공연 가격입니다");
				}
			);

	}

	@Test
	void 옥션_가격__테스트() {
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
			= new GoodsSequenceSeatRequest(this.generalAuctionPrice, null, this.zone, this.auctionSeats);

		// when
		Set<ConstraintViolation<GoodsSequenceSeatRequest>> violations = validator.validate(goodsSequenceSeatRequest);

		//then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("잘못된 경매 가격입니다.");
				}
			);

	}

	@Test
	void 구역_검증_테스트() {
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
			= new GoodsSequenceSeatRequest(this.generalAuctionPrice, this.auctionPrice, null, this.auctionSeats);

		// when
		Set<ConstraintViolation<GoodsSequenceSeatRequest>> violations = validator.validate(goodsSequenceSeatRequest);

		//then
		assertThat(violations).isNotEmpty();
		violations
			.forEach(
				error -> {
					assertThat(error.getMessage()).isEqualTo("구역 입력은 필수입니다.");
				}
			);
	}

	@Test
	void 경매좌석_검증_테스트() {
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
			= new GoodsSequenceSeatRequest(this.generalAuctionPrice, this.auctionPrice, this.zone, this.auctionSeats);

		//then & then
		assertThat(this.auctionSeats.get(0)).isEqualTo(1);
		assertThat(this.auctionSeats.get(1)).isEqualTo(2);
		assertThat(this.auctionSeats.get(2)).isEqualTo(3);
	}

	@Test
	void 일반좌석_생성_테스트() {
		Seat seat = Mockito.mock();
		Sequence sequence = Mockito.mock();
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
			= new GoodsSequenceSeatRequest(this.generalAuctionPrice, this.auctionPrice, this.zone, this.auctionSeats);

		GoodsSequenceSeat goodsSequenceSeat = goodsSequenceSeatRequest.generalToEntity(seat, sequence);

		assertThat(this.generalAuctionPrice).isEqualTo(goodsSequenceSeat.getPrice());
		assertThat(goodsSequenceSeat.getIsSelled()).isFalse();
		assertThat(SellType.NORMAL).isEqualTo(goodsSequenceSeat.getSellType());

	}

	@Test
	void 경매좌석_생성_테스트() {
		Seat seat = Mockito.mock();
		Sequence sequence = Mockito.mock();
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
			= new GoodsSequenceSeatRequest(this.generalAuctionPrice, this.auctionPrice, this.zone, this.auctionSeats);

		GoodsSequenceSeat goodsSequenceSeat = goodsSequenceSeatRequest.auctionToEntity(seat, sequence);

		assertThat(this.auctionPrice).isEqualTo(goodsSequenceSeat.getPrice());
		assertThat(goodsSequenceSeat.getIsSelled()).isFalse();
		assertThat(SellType.AUCTION).isEqualTo(goodsSequenceSeat.getSellType());

	}

}
