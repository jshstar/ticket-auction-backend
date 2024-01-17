package com.sparta.ticketauction.domain.auction.response;

import com.sparta.ticketauction.domain.auction.entity.Auction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuctionInfoResponse {
	private final Long id;
	//입찰가
	private final Long bidPrice;
	//시작가
	private final Long startPrice;
	//남은시간
	private final Long remainTimeMilli;

	public static AuctionInfoResponse from(Auction entity, Long remainTimeMilli) {
		return new AuctionInfoResponse(
			entity.getId(),
			entity.getBidPrice(),
			entity.getStartPrice(),
			remainTimeMilli
		);
	}
}
