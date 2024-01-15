package com.sparta.ticketauction.domain.bid.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.bid.entity.Bid;
import com.sparta.ticketauction.domain.user.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BidRequest {
	@NotNull(message = "입찰가를 입력해주세요.")
	private final Long price;

	@JsonCreator
	public BidRequest(Long price) {
		this.price = price;
	}

	public Bid toEntity(User user, Auction auction) {
		return Bid.builder()
			.price(price)
			.user(user)
			.auction(auction)
			.build();
	}
}