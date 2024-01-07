package com.sparta.ticketauction.domain.admin.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoodsSequenceSeatRequest {
	@NotNull(message = "잘못된 공연 가격입니다")
	private Long generalAuctionPrice;

	@NotNull(message = "잘못된 경매 가격입니다.")
	private Long auctionPrice;

	@Valid
	@NotNull(message = "정확한 경매 좌석을 입력해 주세요.")
	private List<PlaceSeatAuctionInfo> actionSeats;
}
