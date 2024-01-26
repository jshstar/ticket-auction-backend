package com.sparta.ticketauction.domain.auction.response;

import com.sparta.ticketauction.domain.auction.entity.Auction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuctionDetailResponse {
	private final Long id;
	//입찰가
	private final Long bidPrice;
	//시작가
	private final Long startPrice;
	//남은시간
	private final Long remainTimeSeconds;

	public static AuctionDetailResponse from(Auction entity, Long bidPrice) {
		return new AuctionDetailResponse(
			entity.getId(),
			bidPrice,
			entity.getStartPrice(),
			entity.genRemainSeconds()
		);
	}
}
