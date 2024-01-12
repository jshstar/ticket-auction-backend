package com.sparta.ticketauction.domain.admin.request;

import java.util.List;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.SellType;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoodsSequenceSeatRequest {
	@NotNull(message = "잘못된 공연 가격입니다")
	private final Long generalAuctionPrice;

	@NotNull(message = "잘못된 경매 가격입니다.")
	private final Long auctionPrice;

	@NotBlank(message = "구역 입력은 필수입니다.")
	private final String zone;

	private final List<Integer> auctionSeats;

	public GoodsSequenceSeat toEntity(Seat seat, Sequence sequence) {
		return GoodsSequenceSeat
			.builder()
			.seat(seat)
			.sequence(sequence)
			.isSelled(false)
			.build();
	}

	public GoodsSequenceSeat generalToEntity(Seat seat, Sequence sequence) {
		return GoodsSequenceSeat
			.builder()
			.price(this.generalAuctionPrice)
			.seat(seat)
			.sequence(sequence)
			.sellType(SellType.NORMAL)
			.isSelled(false)
			.build();
	}

	public GoodsSequenceSeat auctionToEntity(Seat seat, Sequence sequence) {
		return GoodsSequenceSeat
			.builder()
			.price(this.auctionPrice)
			.seat(seat)
			.sequence(sequence)
			.sellType(SellType.AUCTION)
			.isSelled(false)
			.build();
	}
}
